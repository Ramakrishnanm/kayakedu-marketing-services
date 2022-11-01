package com.kayakedu.marketing.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.kayakedu.marketing.services"})
public class KayakeduMarketingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KayakeduMarketingServiceApplication.class, args);
	}

}
