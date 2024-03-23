package com.sankeit.urlshortner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.sankeit.urlshortner.service.UrlShortnerService;

@SpringBootApplication
public class UrlShortnerApplication {

	@Autowired
	UrlShortnerService service;
	
	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerApplication.class, args);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {
		
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
