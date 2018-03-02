package com.onek.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Classe d'encodage 
 */
public class Encode {
	
	/**
	 * Encode un String en sha1
	 * @param s String a encodé
	 * @return Le String encodé en sha1
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sha1(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Objects.requireNonNull(s);
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();		
		digest.update(s.getBytes("utf8"));	
		return String.format("%040x", new BigInteger(1, digest.digest()));
    } 
	
	/**
	 * Encode un String en sha256
	 * @param s String a encodé
	 * @return Le String encodé en sha1
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sha256(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Objects.requireNonNull(s);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();		
		digest.update(s.getBytes("utf8"));	
		return String.format("%040x", new BigInteger(1, digest.digest()));
    } 
}
