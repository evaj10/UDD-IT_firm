package com.master.udd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@SpringBootApplication
public class UddApplication {

	public static void main(String[] args) {
		SpringApplication.run(UddApplication.class, args);
	}

	@Bean
	public RestTemplate configureRestTemplate() {
		return new RestTemplate();
	}
}
