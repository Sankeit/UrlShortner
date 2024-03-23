package com.sankeit.urlshortner.service;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UniqueKeyGenerator {

    private static final String BASE62_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int RANDOM_PART_LENGTH = 2;

    public String generateUniqueKey() {
        long timestamp = System.currentTimeMillis();
        String randomPart = generateRandomString(RANDOM_PART_LENGTH);
        String base62Key = convertToBase62(timestamp) + randomPart;
        
        // Truncate to maximum length of 5 characters
        if (base62Key.length() > 5) {
            base62Key = base62Key.substring(0, 5);
        }
        
        return base62Key;
    }

    private String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private String convertToBase62(long number) {
        StringBuilder sb = new StringBuilder();

        do {
            sb.insert(0, BASE62_CHARACTERS.charAt((int) (number % 62)));
            number /= 62;
        } while (number > 0);

        return sb.toString();
    }
    
    public static void main(String[] args) {
    	
    	UniqueKeyGenerator generator = new UniqueKeyGenerator();
    	
		System.out.println(generator.convertToBase62(99_999_999L));
	}
}
