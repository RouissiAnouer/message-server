package com.app.config.yaml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(ignoreUnknownFields = true)
public class YamlConfigurationProperties {
	
	@Getter @Setter private ChatApllicationProp application;

}
