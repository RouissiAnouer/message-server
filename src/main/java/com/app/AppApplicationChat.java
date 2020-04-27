package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@ComponentScan
@Configuration
@EnableTransactionManagement
public class AppApplicationChat {

	public static void main(String[] args) {
		helloWorld();
		SpringApplication.run(AppApplicationChat.class, args);
	}

	public static String helloWorld() {
		return "Hello World";
	}

}
