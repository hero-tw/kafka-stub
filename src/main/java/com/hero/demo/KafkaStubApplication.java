package com.hero.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaStubApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStubApplication.class, args);
		KafkaStub stub = new KafkaStub();
		stub.start();
	}
}
