package net.biologeek.bot.tests.application;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan("net.biologeek.bot")
public class ApplicationTestConfiguration {

	@Bean
	public EmbeddedServletContainerFactory tomcat() {
		return new TomcatEmbeddedServletContainerFactory();
	}

}
