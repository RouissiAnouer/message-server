package com.app.config.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.app.config.yaml.YamlConfigurationProperties;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JpaConfig {
    
    @Autowired
    private YamlConfigurationProperties env;
    
    @Bean
    public DataSource dataSource() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(env.getApplication().getDbSQL().getDriverClassName());
            dataSource.setUrl(env.getApplication().getDbSQL().getUrlDB());
            dataSource.setUsername(env.getApplication().getDbSQL().getUserName());
            dataSource.setPassword(env.getApplication().getDbSQL().getPassword());
    		log.info("mySQL is up");
            return dataSource;
    }
    
    @Bean
    public MongoClient mongoDataSource() {
    	log.info("noSQL is up");
        return MongoClients.create(env.getApplication().getDbNoSQL().getUrl());
    }
    
    @Bean
    public MongoTemplate mongoTemplate() {
    	return new MongoTemplate(mongoDataSource(), "chatDB");
    }
    
}