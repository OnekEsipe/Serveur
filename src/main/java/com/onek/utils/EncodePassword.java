package com.onek.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodePassword {

	public static String sha1(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();		
		digest.update(password.getBytes("utf8"));	
		return String.format("%040x", new BigInteger(1, digest.digest()));
    } 
}
