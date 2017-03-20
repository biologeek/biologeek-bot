package net.biologeek.bot.batch.config;

import java.util.Arrays;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import net.biologeek.bot.batch.HttpsReplaceItemProcesor;
import net.biologeek.bot.batch.beans.PurgeSubCategoriesItemProcesor;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.article.ArticleElement;
import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMembers;
import net.biologeek.bot.plugin.beans.batch.readers.CategoryItemReader;
import net.biologeek.bot.plugin.beans.batch.readers.WikipediaArticleItemReader;
import net.biologeek.bot.plugin.beans.batch.writers.ArticleWitnessItemWriter;
import net.biologeek.bot.plugin.beans.batch.writers.WikipediaArticleEditItemWriter;
import net.biologeek.bot.plugin.beans.category.CategoryMembers;
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
	
	@Value("${job.scan.category.name}")
	private String categoryToScan;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	PropertySourcesPlaceholderConfigurer propertySource() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	Job httpsConversionJob() {
		return jobs.get("httpsConversionJob").start(httpsConvertStep()).build();
	}
	
	@Bean
	Step retrieveArticleTitles(){
		return steps.get("retrieveArticles")
				.<CategoryMembers, SimpleCategoryMembers> chunk(50)
				.reader(categoryReader())
				.processor(categoryProcessor())
				.writer(tempCategoryStorage())
				.build();
	}

	/**
	 * Writing to database
	 * @return
	 */
	private ItemWriter<SimpleCategoryMembers> tempCategoryStorage() {
		JpaItemWriter<SimpleCategoryMembers> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	/**
	 * Nothing to do except remove subcategories
	 * 
	 * @return
	 */
	private ItemProcessor<CategoryMembers, SimpleCategoryMembers> categoryProcessor() {
		return new PurgeSubCategoriesItemProcesor();
	}

	private ItemReader<CategoryMembers> categoryReader() {
		return new CategoryItemReader()//
				.title(categoryToScan);
	}

	@Bean
	Step httpsConvertStep() {
		return steps.get("httpsConvertStep")//
				.<ArticleContent, ArticleContent> chunk(10)//
				.reader(articleReader())//
				.processor(itemProcesor())//
				.writer(articleCompositeItemWriter()).build();
	}

	@Bean
	private ItemReader<ArticleContent> articleReader() {
		return new WikipediaArticleItemReader();
	}

	@Bean
	ItemProcessor<ArticleContent, ArticleContent> itemProcesor() {
		return new HttpsReplaceItemProcesor();
	}

	@Bean("articleCompositeItemWriter")
	CompositeItemWriter<ArticleContent> articleCompositeItemWriter() {
		CompositeItemWriter<ArticleContent> ciw = new CompositeItemWriter<>();
		ciw.setDelegates(Arrays.asList(wikipediaArticleEditItemWriter(), articleWitnessItemWriter()));
		return ciw;
	}

	@Bean(name = "articleWitnessItemWriter")
	private ItemWriter<ArticleElement> articleWitnessItemWriter() {
		return new ArticleWitnessItemWriter();
	}

	@Bean(name = "wikipediaArticleEditItemWriter")
	private ItemWriter<ArticleElement> wikipediaArticleEditItemWriter() {
		return new WikipediaArticleEditItemWriter<>();
	}

}
