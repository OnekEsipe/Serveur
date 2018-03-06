package com.onek.resourcestest;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.onek.resource.PasswordResetResource;

public class PasswordResetResourceTest {

	@Test
	public void testEmptyConstructor() {
		new PasswordResetResource();
	}
	
	@Test
	public void testGetterMail() {
		PasswordResetResource prr = new PasswordResetResource();
		assertNull(prr.getMail());
	}
}
