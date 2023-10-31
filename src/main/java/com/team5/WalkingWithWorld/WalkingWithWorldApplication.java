package com.team5.WalkingWithWorld;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.team5.WalkingWithWorld.repository"})
//@MapperScan(value={"com.team5.WalkingWithWorld.dao"})
public class WalkingWithWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalkingWithWorldApplication.class, args);
	}

}
