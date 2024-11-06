package com.absolute.code.weirdo.create_job_post_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CreateJobPostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreateJobPostServiceApplication.class, args);
	}

}
