package com.bazalytskyi.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class TestAssignmentApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(TestAssignmentApplication.class, args);
	}
}
