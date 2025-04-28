package com.example.bookqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BookqueryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookqueryserviceApplication.class, args);
	}

}
