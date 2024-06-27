package com.sankeit.urlshortner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sankeit.urlshortner.model.Url;
import com.sankeit.urlshortner.service.UrlShortnerService;

@SpringBootTest
@Disabled
class ServiceTest {

	@Autowired
	UrlShortnerService service;
	
	@Test
	void test1() {
		
		String longUrl = "https://www.tutorialspoint.com/spring_boot/spring_boot_unit_test_cases.htm";
		
		Url input = new Url();
		
		input.setUrl(longUrl);
		
		Url expected = new Url();
		
		expected.setCreatedOn(new Date());
		expected.setKey("5X5X5");
		expected.setUrl(longUrl);
		
		Url actual = service.shorten(longUrl);
		
		assertEquals(expected.getKey(), actual.getKey());
		assertEquals(expected.getUrl(), actual.getUrl());
	}

}
