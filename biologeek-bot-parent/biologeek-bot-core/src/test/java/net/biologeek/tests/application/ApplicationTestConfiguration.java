package net.biologeek.tests.application;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan("net.biologeek.bot")
@ComponentScan(basePackages = { "net.biologeek.bot.tests" }, excludeFilters={
		@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Configuration.class)
})
public class ApplicationTestConfiguration {

	@Bean
	public EmbeddedServletContainerFactory tomcat() {
		return new TomcatEmbeddedServletContainerFactory();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer configurer (){
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("bdd.properties"));
		return ppc;
	}
	
}
