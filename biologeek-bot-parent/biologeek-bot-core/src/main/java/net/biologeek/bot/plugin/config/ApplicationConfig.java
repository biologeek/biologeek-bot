package net.biologeek.bot.plugin.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.biologeek.bot.wiki.client.Country;
import net.biologeek.bot.wiki.client.Wikipedia;
import net.biologeek.bot.wiki.client.Wikipedia.WikipediaBuilder;

@Configuration
@ComponentScan("net.biologeek.bot")
@EnableJpaRepositories
@EnableTransactionManagement
@PropertySources({ // @PropertySource("file://${app.parameters}/configuration.properties"),
		@PropertySource("file:${app.parameters}/bdd.properties") })
/**
 * Datasource building consists of JNDI lookup over comp/env/jdbc/wikibot
 */
public class ApplicationConfig implements EnvironmentAware{


	@Value("${jdbc.connection.driver}")
	protected String connectionDriverClassName;

	@Value("${jdbc.connection.url}")
	protected String connectionURL;

	@Value("${jdbc.connection.user}")
	protected String connectionUserName;

	@Value("${jdbc.connection.password")
	protected String connectionPassword;

	@Value("${hibernate.dialect}")
	private String dialect;

	@Autowired
	private Environment env;

	@Value("${country.restrictions}")
	private String country;

	@Value("${address.baseurl}")
	private String baseURL;

	@Value("${api.maxlogins}")
	private String maxLogins;
	
	@Bean
	public EmbeddedServletContainerFactory tomcat() {
		return new TomcatEmbeddedServletContainerFactory();
	}

	@Bean
	public DataSource datasource() throws Exception {
		BasicDataSource dataSource = new BasicDataSource();


		dataSource.setDriverClassName(env.getProperty("jdbc.connection.driver"));
		dataSource.setUrl(env.getProperty("jdbc.connection.url"));
		dataSource.setUsername(env.getProperty("jdbc.connection.user"));
		dataSource.setPassword(env.getProperty("jdbc.connection.password"));

		return dataSource;
	}

	@Bean
	JpaTransactionManager jpaTransactionManager(EntityManagerFactory factory) {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(factory);
		return manager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(ds);
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("net.biologeek.bot.plugin.beans");

		emf.setJpaProperties(properties());
		return emf;
	}

	private Properties properties() {
		Properties props = new Properties();
		props.put("hibernate.dialect", dialect);
		props.put("hibernate.show_sql", true);
		props.put("hibernate.hbm2ddl.auto", "update");
		
		return props;
	}

	@Override
	public void setEnvironment(Environment environment) {
		env = environment;		
	}
	
	@Bean
	Wikipedia wikipedia(){
		Wikipedia.WikipediaBuilder builder = new WikipediaBuilder(null);
		if (country != null && !country.equals("")){
			List<Country> countries = processCountries(country);
			builder.languages(countries);			
		}
		
		if (baseURL != null && !baseURL.equals("")){
			builder.baseURL(baseURL);
		}
		
		if (maxLogins != null && !maxLogins.equals("")){
			builder.maxLogins(Integer.valueOf(maxLogins));
		}
		
		return builder.build();
	}

	private List<Country> processCountries(String country2) {
		List<Country> res = new ArrayList<>();
		String[] splited = country2.split(";");
		for (String elt : splited){
			if (Country.contains(elt)){
				res.add(Country.valueOf(elt));
			}
		}
		return res;
	}

}
