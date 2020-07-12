package com.app.config.firebase.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import com.app.config.firebase.PushNotificationService;
import com.app.config.firebase.Model.PushObjectFcm;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class PushNotificationServiceBuilder {
	
	@Autowired
    ResourceLoader resourceLoader;
	
	@Bean
	@Autowired
	public PushNotificationService NotificationService() throws IOException {
		PushNotificationConfig config = PushNotificationConfig.instance();
		FileInputStream serviceAccount  = new FileInputStream(ResourceUtils.getFile("classpath:mymessenger.json"));
		PushObjectFcm obj = PushObjectFcm.builder().appName("com.my.messenger").fileJsonPath(serviceAccount).build();
		config.addApp(obj);
		log.info("Start Push Notification Service");
		return PushNotificationService.builder().config(config).build();
	}

}
