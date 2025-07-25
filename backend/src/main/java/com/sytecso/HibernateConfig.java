package com.sytecso;

import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.sytecso.config.ConfigFiles;
import com.sytecso.config.PropertiesFile;
import com.sytecso.config.logger.SytecsoLogger;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {


	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public LocalSessionFactoryBean entityManagerFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.sytecso.model");
		sessionFactory.setHibernateProperties(additionalProperties());
		return sessionFactory;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(this.dataSource());
	}

	@Bean
	public DataSource dataSource() {
		Properties prop = null;
		try {
			 prop= new PropertiesFile().getPropValues(new ConfigFiles().getStringSO()+"jdbc.properties");
		} catch (IOException e) {
			SytecsoLogger.error("ERROR AL TRATAR DE LEER EL  ARCHIVO DE CONFIGURACIÓN DE LA  BASE DE DATOS", e);
		} 
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(prop.getProperty("jdbc.driverClassName"));
		dataSource.setUsername(prop.getProperty("jdbc.username"));
		dataSource.setPassword(prop.getProperty("jdbc.password"));
		String ip=prop.getProperty("jdbc.ip");
		String port=prop.getProperty("jdbc.port");
		String propertyConnection=prop.getProperty("jdbc.connectionProperty");
		String schema=prop.getProperty("jdbc.schema");
		String aditional= prop.getProperty("jdbc.stringAditional");
		dataSource.setUrl(propertyConnection+ip+":"+port+"/"+schema+"?"+aditional);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		// properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("logging.level.org.hibernate.SQL", "DEBUG");
		properties.setProperty("logging.level.org.hibernate.type", "TRACE");
		
		return properties;
	}
}
