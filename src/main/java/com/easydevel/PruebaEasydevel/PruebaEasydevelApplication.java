package com.easydevel.PruebaEasydevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PruebaEasydevelApplication {

	public static void main(final String[] args) {
		SpringApplication.run(PruebaEasydevelApplication.class, args);
	}

}
