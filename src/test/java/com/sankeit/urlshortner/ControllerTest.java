package com.sankeit.urlshortner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankeit.urlshortner.controller.UrlShortnerController;
import com.sankeit.urlshortner.model.Url;
import com.sankeit.urlshortner.service.UrlShortnerService;

@WebMvcTest(controllers = UrlShortnerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UrlShortnerService service;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	@Test
	public void UrlShortnerController_Shorten_ReturnUrl() throws Exception {
	
		String longUrl = "https://www.tutorialspoint.com/spring_boot/spring_boot_unit_test_cases.htm";
		
		Url expected = new Url();

    	expected.setKey("5X5XY");
    	expected.setUrl(longUrl);
    	expected.setCreatedOn(new Date());

		when(service.shorten(anyString(), anyString())).thenReturn(expected);
		
		mockMvc.perform(post("/shorten")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("url", longUrl)
				.param("key", ""))
		.andExpectAll(MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
				MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected))
				);
	}
	
}
