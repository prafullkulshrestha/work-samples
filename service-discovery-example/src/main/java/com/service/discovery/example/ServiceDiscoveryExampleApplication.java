package com.service.discovery.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceDiscoveryExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoveryExampleApplication.class, args);
	}

}
