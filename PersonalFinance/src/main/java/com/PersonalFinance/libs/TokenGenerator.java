package com.PersonalFinance.libs;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
	
	private static final SecureRandom secureRandom = new SecureRandom(); 
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); 
	private static TokenGenerator instance;
	
	public TokenGenerator() {}
	
	public static synchronized TokenGenerator getInstance() {
		if (instance ==null) {
			instance = new TokenGenerator();
		}
		return instance;
	}

	public static String generatedToken() {
	    byte[] randomBytes = new byte[6];
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}
}
