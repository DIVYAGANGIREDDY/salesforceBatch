package com.salesforcebatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalesforceBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesforceBatchApplication.class, args);
	}

}
