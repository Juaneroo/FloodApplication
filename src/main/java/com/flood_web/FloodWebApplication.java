package com.flood_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = "com.flood_web.data.entity")
@EnableJpaRepositories(basePackages = "com.flood_web.data.repository")

@EnableScheduling
@SpringBootApplication
public class FloodWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(FloodWebApplication.class, args);
	}
}
