package com.sankeit.urlshortner.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.sankeit.urlshortner.model.Url;

public class UrlKeyGenerator implements IdentifierGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String base62ShuffledChars = "6ItpLbX2eNCavl7hFn5uYsUi0z4EfPqVDOWGgZm1k8cATQ9oJxdKSj3HyRMwBr";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		// Get the ID value from the trigger
		Long id = (Long) session.createNativeQuery("SELECT NEXTVAL('URL_SEQUENCE')", Long.class).uniqueResult();

		// Set the ID value on the object
		
		String key = toBase62(id);
		
		((Url) object).setKey(key);

		System.out.println(getClass().getSimpleName() + ":: Key created: " + key);
		return key;
	}

	private String toBase62(long number) {
//		String base62Characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String base62Characters = base62ShuffledChars;
		StringBuilder sb = new StringBuilder();
		do {
			sb.insert(0, base62Characters.charAt((int) (number % 62)));
			number /= 62;
		} while (number > 0);
		return sb.toString();
	}
	
	private int toBase10From62(String base62Number) {
		int base10Number = 0;
        for (int i = 0; i < base62Number.length(); i++) {
            int digit = Character.getNumericValue(base62Number.charAt(i));
            base10Number += digit * Math.pow(62, base62Number.length() - i - 1);
        }
        return base10Number;
	}
	
	public static void main(String[] args) {
		System.out.println(new UrlKeyGenerator().toBase10From62("I6I6I"));
	}
	
}