package com.app.config.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.app.config.yaml.YamlConfigurationProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JpaConfig {
    
    @Autowired
    private YamlConfigurationProperties env;
    
    @Bean
    public DataSource dataSource() {
    	log.info("db url : " + env.getApplication().getDb().getUrlDB());
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getApplication().getDb().getDriverClassName());
        dataSource.setUrl(env.getApplication().getDb().getUrlDB());
        dataSource.setUsername(env.getApplication().getDb().getUserName());
        dataSource.setPassword(env.getApplication().getDb().getPassword());
        return dataSource;
    }
    
}