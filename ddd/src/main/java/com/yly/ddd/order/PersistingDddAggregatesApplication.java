package com.yly.ddd.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.yly.ddd.order")
public class PersistingDddAggregatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersistingDddAggregatesApplication.class, args);
	}
}
