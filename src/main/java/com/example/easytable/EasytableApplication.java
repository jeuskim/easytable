package com.example.easytable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EasytableApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasytableApplication.class, args);
	}

}
