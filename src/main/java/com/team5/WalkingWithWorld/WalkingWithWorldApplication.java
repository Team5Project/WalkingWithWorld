package com.team5.WalkingWithWorld;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.team5.WalkingWithWorld"})
@MapperScan(value={"com.team5.WalkingWithWorld.dao"})
@EnableJpaRepositories(value={"com.team5.WalkingWithWorld.repository"})
@EntityScan(value = {"com.team5.WalkingWithWorld.entity"})
public class WalkingWithWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalkingWithWorldApplication.class, args);
	}

}
