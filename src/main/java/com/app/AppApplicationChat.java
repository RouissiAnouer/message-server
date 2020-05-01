package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
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
