package com.team5.WalkingWithWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableJpaRepositories
public class WalkingWithWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalkingWithWorldApplication.class, args);
	}

}
