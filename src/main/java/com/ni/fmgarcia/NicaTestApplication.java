package com.ni.fmgarcia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:swagger-documentation.properties")
public class NicaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(NicaTestApplication.class, args);
	}

}
