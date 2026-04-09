package com.example.oliveold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OliveoldApplication {

	public static void main(String[] args) {
		SpringApplication.run(OliveoldApplication.class, args);
	}

}
