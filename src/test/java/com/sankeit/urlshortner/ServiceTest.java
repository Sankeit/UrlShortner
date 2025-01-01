package com.sankeit.urlshortner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sankeit.urlshortner.dao.UrlDao;
import com.sankeit.urlshortner.model.Url;
import com.sankeit.urlshortner.service.UrlShortnerServiceImpl;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ServiceTest {

	@InjectMocks
	UrlShortnerServiceImpl service;
	
	@Mock
	UrlDao dao;
	
	@BeforeEach
    public void setUp() {
		// Alternate way to setup mock objects
//        dao = Mockito.mock(UrlDao.class);
//        service = new UrlShortnerServiceImpl(dao);
    }

    @Test
    public void testGetData() {

    	
    	Url expected = new Url();

    	expected.setKey("5X5XY");
    	expected.setUrl("https://www.tutorialspoint.com/spring_boot/spring_boot_unit_test_cases.htm");

        // Arrange
        when(dao.save(any(Url.class))).thenReturn(expected);

        // Act
        Url actual = service.shorten("https://www.tutorialspoint.com/spring_boot/spring_boot_unit_test_cases.htm");

        // Assert
        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getKey(), actual.getKey());

        // Verify
        verify(dao, times(1)).save(any(Url.class));
    }

}
