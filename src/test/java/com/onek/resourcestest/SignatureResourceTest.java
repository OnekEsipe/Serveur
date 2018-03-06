package com.onek.resourcestest;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.onek.model.Signature;
import com.onek.resource.SignatureResource;

public class SignatureResourceTest {

	@Test
	public void testEmptyConstructor() {
		new SignatureResource();
	}
	
	@Test
	public void testCreateSignature() {
		SignatureResource sr = new SignatureResource();
		Signature s = sr.createSignature();
		assertNull(s.getNom());
		assertNull(s.getSignature());
	}
}
