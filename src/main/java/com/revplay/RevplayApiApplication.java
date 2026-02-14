package com.revplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@EnableConfigurationProperties
@SpringBootApplication
public class RevplayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevplayApiApplication.class, args);
	}

}
