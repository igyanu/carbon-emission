package com.emissiontracker.emission_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EmissionTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmissionTrackerApplication.class, args);
	}

}
