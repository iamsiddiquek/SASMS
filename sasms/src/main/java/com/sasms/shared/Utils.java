package com.sasms.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public String generateRandomPublicId(Integer length) {
		return generateRandomString(length);
	}

	private String generateRandomString(Integer length) {
		StringBuilder returnValue = new StringBuilder(length);
		
		for(int i=0; i<length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return returnValue.toString();
	}
	
}
