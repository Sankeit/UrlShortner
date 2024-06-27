package com.sankeit.urlshortner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.sankeit.urlshortner.service.UrlShortnerService;

@SpringBootApplication
public class UrlShortnerApplication extends SpringBootServletInitializer {

	@Autowired
	UrlShortnerService service;
	
	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerApplication.class, args);
		System.out.println("Application started!");
	}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {
		
		httpSec.authorizeHttpRequests(customizer -> 
		customizer
		.requestMatchers(HttpMethod.POST, "/shorten").authenticated()
		.anyRequest().permitAll()
				);

		httpSec.httpBasic(Customizer.withDefaults());

		httpSec.csrf(csrf -> csrf.disable());
		
		httpSec.headers(customizer -> customizer.frameOptions(c -> c.disable()));

		return httpSec.build();
	}
}
