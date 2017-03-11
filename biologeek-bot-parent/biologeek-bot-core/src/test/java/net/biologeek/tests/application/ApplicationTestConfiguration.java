package net.biologeek.tests.application;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "net.biologeek.bot" })
@PropertySources({ @PropertySource("classpath:bdd.properties"), @PropertySource("classpath:configuration.properties") })
@EnableJpaRepositories(basePackages="net.biologeek.bot")
@EnableTransactionManagement
public class ApplicationTestConfiguration {


	@Value("${hibernate.dialect}")
	private String dialect;
	@Bean
	public EmbeddedServletContainerFactory tomcat() {

		return new TomcatEmbeddedServletContainerFactory();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer configurer() {
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		return ppc;
	}
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(ds);
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("net.biologeek.bot.plugin");

		emf.setJpaProperties(properties());
		return emf;
	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().generateUniqueName(true)
				.setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				.ignoreFailedDrops(true)
				.build();
				
	}

	private Properties properties() {
		Properties props = new Properties();
		props.put("hibernate.dialect", dialect);
		props.put("hibernate.show_sql", true);
		props.put("hibernate.hbm2ddl.auto", "update");
		
		return props;
	}
	
}
