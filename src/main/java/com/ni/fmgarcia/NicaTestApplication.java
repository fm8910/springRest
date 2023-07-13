package com.ni.fmgarcia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class NicaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(NicaTestApplication.class, args);
	}

}
