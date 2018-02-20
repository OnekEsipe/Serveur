package com.onek.utils;

import java.security.SecureRandom;

public class PasswordGenerator {

	private static SecureRandom random = new SecureRandom();

	/** different dictionaries used */
	private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	private final String NUMERIC = "0123456789";

	private static String initGeneratePassword(int len, String dic) {
		String result = "";
		for (int i = 0; i < len; i++) {
			int index = random.nextInt(dic.length());
			result += dic.charAt(index);
		}
		return result;
	}

	public String generatePassword(int length) {
		if(length < 1 || length > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Length must be sup 0 and inf Integer.MAX_VALUE");
		}
		String password = initGeneratePassword(length, ALPHA_CAPS + ALPHA + NUMERIC);
		return password;
	}
	
	public String generateCode(int length) {
		if(length < 1 || length > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Length must be sup 0 and inf Integer.MAX_VALUE");
		}
		String password = initGeneratePassword(length, NUMERIC);
		return password;
	}
}