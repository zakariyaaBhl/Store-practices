package com.bhl.store.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.bhl.store")
@PropertySource("classpath:security-persistence-mysql.properties")
public class SpringMvcConfigForSecurity implements WebMvcConfigurer{
	
	@Autowired
	private Environment env;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	
	@Bean
	public DataSource dataSourceForSecurity() {
		ComboPooledDataSource dataSource=new ComboPooledDataSource();
			try {
				dataSource.setDriverClass(getPropFromFile("security.jdbc.driver"));
				
				logger.info("\n=====>>> Security USER : "+ env.getProperty("security.jdbc.user"));
				logger.info("\n=====>>> Security URL : "+ env.getProperty("security.jdbc.url"));
				
				dataSource.setJdbcUrl(getPropFromFile("security.jdbc.url"));
				dataSource.setUser(getPropFromFile("security.jdbc.user"));
				dataSource.setPassword(getPropFromFile("security.jdbc.password"));
				
				dataSource.setInitialPoolSize(Integer.parseInt(getPropFromFile("security.connection.pool.initialPoolSize")));
				dataSource.setMinPoolSize(Integer.parseInt(getPropFromFile("security.connection.pool.minPoolSize")));
				dataSource.setMaxPoolSize(Integer.parseInt(getPropFromFile("security.connection.pool.maxPoolSize")));
				dataSource.setMaxIdleTime(Integer.parseInt(getPropFromFile("security.connection.pool.maxIdleTime")));
				
			} catch (PropertyVetoException e) {
				new RuntimeException(e);
			}
		return dataSource;
	}
	
	public Properties getHibernatePropsFromFile() {
		Properties properties= new Properties();
		properties.put("security.hibernate.dialect", getPropFromFile("security.hibernate.dialect"));
		properties.put("security.hibernate.show_sql", getPropFromFile("security.hibernate.show_sql"));
		properties.put("security.hibernate.hbm2ddl.auto", "update");
		return properties; 
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactoryBeanForSecurity() {
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		factory.setDataSource(dataSourceForSecurity());
		factory.setHibernateProperties(getHibernatePropsFromFile());
		factory.setPackagesToScan("com.bhl.store.entities");
		return factory;
	}
	
	@Bean
	public HibernateTransactionManager transactionManagerForSecurity() {
		HibernateTransactionManager tx = new HibernateTransactionManager();
		tx.setSessionFactory(sessionFactoryBeanForSecurity().getObject());
		return tx;
	}
	private String getPropFromFile(String propName) {
		return env.getProperty(propName);
	}

}
