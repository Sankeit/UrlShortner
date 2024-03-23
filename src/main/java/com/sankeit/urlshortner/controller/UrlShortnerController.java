package com.sankeit.urlshortner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sankeit.urlshortner.model.Url;
import com.sankeit.urlshortner.service.UrlShortnerService;

import jakarta.servlet.http.HttpServletResponse;

@Controller 
public class UrlShortnerController {

	UrlShortnerService service;

	@Autowired
	public UrlShortnerController(UrlShortnerService service) {
		this.service = service;
	}

	@GetMapping("/{key}")
	public void goToUrl(@PathVariable String key, HttpServletResponse httpServletResponse) {

		Url urlObj = service.findByKey(key);
		
		if(urlObj == null) {
			throw new RuntimeException("Shortened URL not found: " + key);
		}
		
		httpServletResponse.setHeader("Location", urlObj.getUrl());
		httpServletResponse.setStatus(302);
	}
	
	@PostMapping("/shorten")
	public ResponseEntity<Url> shortenUrl(@RequestParam("url") String longUrl) {
		
		if(!StringUtils.hasText(longUrl)) {
			return ResponseEntity.badRequest().build();
		}
		
		Url url = service.shorten(longUrl);
		
		return ResponseEntity.ok(url);
	}
}
