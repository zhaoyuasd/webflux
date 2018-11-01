package com.laozhao.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WebfluxPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPublisherApplication.class, args);
	}
}
