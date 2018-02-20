package com.onek.utilstest;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.onek.utils.Encode;

public class EncodeTests {

	@Test(expected=NullPointerException.class)
	public void testSha1NotNull() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Encode.sha1(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testSha256NotNull() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Encode.sha256(null);
	}
	
	@Test
	public void testsEncodeEmptyString() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		assertNotNull(Encode.sha1(""));
		assertNotNull(Encode.sha256(""));
	}
	
	@Test
	public void testsSameEncodeForSameValue() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		assertEquals(Encode.sha1("toto"), Encode.sha1("toto"));
		assertEquals(Encode.sha256("toto"), Encode.sha256("toto"));
	}
	
	@Test
	public void testsGoodResultEncodeSha1() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		assertEquals("0b9c2625dc21ef05f6ad4ddf47c5f203837aa32c", Encode.sha1("toto"));
		assertEquals("7014b783a23400a7bb2e385eceb2848529703be5", Encode.sha1("fsdf*sf*s**/@&"));
	}
	
	@Test
	public void testsGoodResultEncodeSha256() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		assertEquals("31f7a65e315586ac198bd798b6629ce4903d0899476d5741a9f32e2e521b6a66", Encode.sha256("toto"));
		assertEquals("678585b9ec17c375a9be5948417ad43e6de21d9fa4bc53931829254858d972e0", Encode.sha256("fsdf*sf*s**/@&"));
	}
	

	


}
