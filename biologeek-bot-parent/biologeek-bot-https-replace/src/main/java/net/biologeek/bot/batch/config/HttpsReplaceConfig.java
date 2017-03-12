package net.biologeek.bot.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import net.biologeek.bot.batch.HttpsReplaceItempProcesor;
import net.biologeek.bot.batch.HttpsReplaceJob;
import net.biologeek.bot.plugin.config.ApplicationConfig;

@Configuration
@PropertySources({ @PropertySource("classpath:src/main/resources/application.properties"),
		@PropertySource("classpath:src/main/resources/bdd.properties") })
@Import(value = ApplicationConfig.class)
@EnableBatchProcessing
public class HttpsReplaceConfig {
	
	@Bean
	PropertySourcesPlaceholderConfigurer propertySource(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	Job job (){
		return new HttpsReplaceJob();
	}
	
	Step step(){
		return null;		
	}
	
	@Bean
	ItemProcessor<?, ?> itemProcesor(){
		return new HttpsReplaceItempProcesor();
	}
}
