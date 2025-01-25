package com.backend.localshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LocalShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalShareApplication.class, args);
	}

}
