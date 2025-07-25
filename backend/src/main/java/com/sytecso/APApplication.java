package com.sytecso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sytecso.*")
public class APApplication {
	public static void main(String[] args) {
		SpringApplication.run(APApplication.class, args);
	}
}



