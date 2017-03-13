package net.biologeek.bot.batch.config;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import net.biologeek.bot.api.plugin.article.Article;
import net.biologeek.bot.api.plugin.article.ArticleContent;
import net.biologeek.bot.batch.HttpsReplaceItempProcesor;
import net.biologeek.bot.batch.HttpsReplaceJob;
import net.biologeek.bot.plugin.beans.article.ArticleElement;
import net.biologeek.bot.plugin.beans.batch.readers.WikipediaArticleItemReader;
import net.biologeek.bot.plugin.beans.batch.writers.ArticleWitnessItemWriter;
import net.biologeek.bot.plugin.beans.batch.writers.WikipediaArticleEditItemWriter;
import net.biologeek.bot.plugin.config.ApplicationConfig;

@Configuration
@PropertySources({ @PropertySource("classpath:src/main/resources/application.properties"),
		@PropertySource("classpath:src/main/resources/bdd.properties") })
@Import(value = ApplicationConfig.class)
@EnableBatchProcessing
public class HttpsReplaceConfig {
	
	@Autowired
	JobBuilderFactory jobs;
	
	@Autowired
	StepBuilderFactory steps;
	
	@Bean
	PropertySourcesPlaceholderConfigurer propertySource(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	Job httpsConversionJob (){
		return jobs.get("httpsConversionJob").start(httpsConvertStep()).build();
	}
	
	Step httpsConvertStep(){
		return steps.get("httpsConvertStep")//
				.<ArticleContent, ArticleContent> chunk(10)//
				.reader(articleReader())//
				.processor(itemProcesor())//
				.writer(articleCompositeItemWriter())
				.build();		
	}
	
	@Bean
	private ItemReader<? extends Article<?>> articleReader() {
		return new WikipediaArticleItemReader();
	}

	@Bean
	ItemProcessor<?, ?> itemProcesor(){
		return new HttpsReplaceItempProcesor();
	}
	
	@Bean("articleCompositeItemWriter")
	CompositeItemWriter<ArticleElement> articleCompositeItemWriter(){
		CompositeItemWriter<ArticleElement> ciw = new CompositeItemWriter<>();
		ciw.setDelegates(Arrays.asList(wikipediaArticleEditItemWriter(), articleWitnessItemWriter()));
		return ciw;
	}

	@Bean(name="articleWitnessItemWriter")
	private ItemWriter<ArticleElement> articleWitnessItemWriter() {
		return new ArticleWitnessItemWriter();
	}

	@Bean(name="wikipediaArticleEditItemWriter")
	private ItemWriter<ArticleElement> wikipediaArticleEditItemWriter() {
		return new WikipediaArticleEditItemWriter<>();
	}

}
