package com.team5.WalkingWithWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.team5.WalkingWithWorld"})
//@MapperScan(value={"com.team5.WalkingWithWorld.dao"})
public class WalkingWithWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalkingWithWorldApplication.class, args);
	}

}
