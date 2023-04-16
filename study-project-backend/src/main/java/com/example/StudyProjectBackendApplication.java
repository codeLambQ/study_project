package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@ComponentScan("com.example.mapper") 会使 spring security 出问题
public class StudyProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyProjectBackendApplication.class, args);
	}

}
