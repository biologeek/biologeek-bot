package net.biologeek.bot.plugin.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("net.biologeek.bot")
@EnableJpaRepositories
@EnableTransactionManagement
@PropertySources({ // @PropertySource("file://${app.parameters}/configuration.properties"),
		@PropertySource("file:${app.parameters}/bdd.properties") })
/**
 * Datasource building consists of JNDI lookup over comp/env/jdbc/wikibot
 */
public class ApplicationConfig {

	@Value("${jdbc.connection.jndiname}")
	protected String connectionJndiName;

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

	@Bean
	public EmbeddedServletContainerFactory tomcat() {
		return new TomcatEmbeddedServletContainerFactory() {

			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();
				resource.setName(connectionJndiName);
				resource.setType(DataSource.class.getName());
				resource.setProperty("driverClassName", connectionDriverClassName);
				resource.setProperty("url", connectionURL);
				resource.setProperty("password", connectionPassword);
				resource.setProperty("username", connectionUserName);
				resource.setType("javax.sql.DataSource");
				context.getNamingResources().addResource(resource);
			}
		};
	}

	@Bean
	public DataSource datasource() throws Exception {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:comp/env/" + connectionJndiName);

		try {
			bean.afterPropertiesSet();
		} catch (IllegalArgumentException | NamingException e) {
			e.printStackTrace();
			throw new Exception("Could not find JNDI datasource");
		}

		return (DataSource) bean.getObject();
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

}
