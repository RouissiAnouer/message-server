package com.app.config.database;

import java.util.ArrayList;
import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.app.config.entity.RoleEntity;
import com.app.config.yaml.YamlConfigurationProperties;
import com.app.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JpaConfig {
    
    @Autowired
    private YamlConfigurationProperties env;
    
    @Autowired
    private RoleRepository roleRep;
    
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
    
    @EventListener
	public void appReady(ApplicationReadyEvent event) {
		if (roleRep.count() != 2) {
			roleRep.deleteAll();
			RoleEntity roleUser = new RoleEntity();
			roleUser.setName("ROLE_USER");
			
			RoleEntity roleAdmin = new RoleEntity();
			roleAdmin.setName("ROLE_ADMIN");
			
			roleRep.save(roleUser);
			roleRep.save(roleAdmin);
			log.info("role setted up successfully");
			
		} else {
			log.info("role already setted");
		}
	}
    
}