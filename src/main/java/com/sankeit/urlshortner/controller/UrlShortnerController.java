package com.sankeit.urlshortner.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sankeit.urlshortner.model.Url;
import com.sankeit.urlshortner.service.UrlShortnerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller 
public class UrlShortnerController {

	UrlShortnerService service;

	public UrlShortnerController(UrlShortnerService service) {
		this.service = service;
	}

	@GetMapping("/{key}")
	public void goToUrl(@PathVariable String key, HttpServletRequest request, HttpServletResponse httpServletResponse) {

		if(!StringUtils.hasText(key) || key.equals("shorten") || key.equals("favicon.ico")) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Url urlObj = service.findByKey(key);

		if(urlObj == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String targetUrl =  urlObj.getUrl();

		if(!request.getParameterMap().isEmpty()) {

			boolean hasEmbeddedQueryParams = false;
			
			StringBuilder query = new StringBuilder();

			if(!urlObj.getUrl().contains("?")) {
				query.append("?");
			}
			else {
				query.append("&");
				hasEmbeddedQueryParams = true;
			}

			List<String> embeddedQueryParams = hasEmbeddedQueryParams?
					Arrays.asList(urlObj.getUrl().split("\\?")[1].split("=")): List.of();

			List<String> queryParams = request.getParameterMap().keySet().stream().toList();

			for(int qI = 0 ; qI < queryParams.size() ; qI++) {

				String queryParam = queryParams.get(qI);

				if(!embeddedQueryParams.contains(queryParam)) {
					
					query.append(queryParam);
					query.append("=");
					query.append(request.getParameter(queryParam));

					if(qI < queryParams.size() - 1) {
						query.append("&");
					}
				}
			}
			
			char queryEndsWith = query.charAt(query.length() - 1);
			
			if(queryEndsWith == '?' || queryEndsWith == '&') {
				query.deleteCharAt(query.length() - 1);
			}

			targetUrl = urlObj.getUrl() + query.toString();
		}

		httpServletResponse.setHeader("Location", targetUrl);
		httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	}
	
	@PostMapping("/shorten")
	public ResponseEntity<Url> shortenUrl(
			@RequestParam("url") String longUrl,
			@RequestParam("key") String customKey,
			HttpServletRequest request) {

		if(!StringUtils.hasText(longUrl)) {
			return ResponseEntity.badRequest().build();
		}

		
		try {
			new URL(longUrl);
			
		} catch (MalformedURLException e) {
			throw new RuntimeException("Invalid URL");
		}
		
		if(StringUtils.hasText(customKey) && (customKey.length() > 20 || customKey.length() < 6)) {
			throw new RuntimeException("Key must be between 6 to 20 chars long");
		}

		Url url = service.findByLongUrl(longUrl);

		if(url == null) {

			if(service.findByKey(customKey) != null) {
				throw new RuntimeException("Key '" + customKey + "' is already used by another URL!");
			}

			url = service.shorten(longUrl, customKey);
		}

		return ResponseEntity.ok(url);
	}
	
	@GetMapping("/Url")
	public ResponseEntity<Url> getUrl(@RequestParam("key") String key) {
		
		Url url = service.findByKey(key);

		if(url == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(url);
	}
	
}
