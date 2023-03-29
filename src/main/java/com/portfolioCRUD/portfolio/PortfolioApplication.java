package com.portfolioCRUD.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages = {"com.portfolioCRUD.portfolio.*"})
@EnableJpaRepositories(basePackages = {"com.portfolioCRUD.portfolio.*"})
@EntityScan(basePackages = {"com.portfolioCRUD.portfolio.*"})
@EnableAutoConfiguration
@SpringBootApplication(exclude = {RepositoryRestMvcAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class PortfolioApplication {


	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

}
