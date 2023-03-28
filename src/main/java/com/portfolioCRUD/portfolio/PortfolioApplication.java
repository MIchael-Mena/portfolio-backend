package com.portfolioCRUD.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.portfolioCRUD.portfolio.*"})
@EnableJpaRepositories(basePackages = {"com.portfolioCRUD.portfolio.repository", "com.portfolioCRUD.portfolio.security.repository"})
@EntityScan(basePackages = {"com.portfolioCRUD.portfolio.model", "com.portfolioCRUD.portfolio.security.entity"})
@SpringBootApplication
public class PortfolioApplication {

//	private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

}
