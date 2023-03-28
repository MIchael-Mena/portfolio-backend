package com.portfolioCRUD.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/*@EnableConfigurationProperties
@EntityScan(basePackages = {"com.portfolioCRUD.portfolio.Model"})*/
@SpringBootApplication
public class PortfolioApplication {

//	private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
//		PortfolioApplication.applicationContext = SpringApplication.run(PortfolioApplication.class, args);
	}

}
