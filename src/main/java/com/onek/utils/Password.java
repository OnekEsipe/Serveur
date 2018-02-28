package com.onek.utils;

import java.security.SecureRandom;

public class Password {
	
	private final static SecureRandom random = new SecureRandom();

	/** different dictionaries used */
	private final static String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final static String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMERIC = "0123456789";

	private static String initGeneratePassword(int len, String dic) {
		String result = "";
		for (int i = 0; i < len; i++) {
			int index = random.nextInt(dic.length());
			result += dic.charAt(index);
		}
		return result;
	}

	public static String generatePassword(int length) {
		if(length < 1 || length > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("length must be positive and inf Integer.MAX_VALUE");
		}
		String password = initGeneratePassword(length, ALPHA_CAPS + ALPHA + NUMERIC);
		return password;
	}
	
	public static String generateCode(int length) {
		if(length < 1 || length > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("length must be positive and inf Integer.MAX_VALUE");
		}
		String password = initGeneratePassword(length, NUMERIC);
		return password;
	}
	
	public static boolean verifyPasswordRule(String password) {
		if (password.length() >= 6 && !password.toLowerCase().equals(password)) {
			return true;
		}
		return false;
	}

}
