package com.onek.utils;

import java.security.SecureRandom;

public class Password {
	
	private final static SecureRandom random = new SecureRandom();

	/** different dictionaries used */
	private final static String ALPHA_CAPS = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	private final static String ALPHA = "abcdefghijkmnopqrstuvwxyz";
	private static final String NUMERIC = "0123456789";

	private static String initGeneratePassword(int len, String dic) {
		String result = "";
		for (int i = 0; i < len; i++) {
			int index = random.nextInt(dic.length());
			result += dic.charAt(index);
		}
		return result;
	}

	/**
	 * Génère un mot de passe en utilisant différents dictionnaires (ALPHA, ALPA_CAPS, NUMERIC).
	 * Les lettres I, l sont volontairement ommises pour des soucis de lectures
	 * @param length Longueur du mot de passe
	 * @return Le mot de passe généré
	 */
	public static String generatePassword(int length) {
		if(length < 1 || length > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("length must be positive and inf Integer.MAX_VALUE");
		}
		String password = initGeneratePassword(length, ALPHA_CAPS + ALPHA + NUMERIC);
		return password;
	}
	
	/**
	 * Génère un code événement avec le dictionnaire NUMERIC
	 * @param length Longueur du code
	 * @return Le code événement généré
	 */
	public static String generateCode(int length) {
		if(length < 1 || length > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("length must be positive and inf Integer.MAX_VALUE");
		}
		String password = initGeneratePassword(length, NUMERIC);
		return password;
	}
	
	/**
	 * Vérifie la règle des mots de passe : Au moins 6 caractères et au moins 1 majuscule
	 * @param password Mot de passe a verifier
	 * @return True si la règle est respectée
	 */
	public static boolean verifyPasswordRule(String password) {
		if (password.length() >= 6 && !password.toLowerCase().equals(password)) {
			return true;
		}
		return false;
	}

}
