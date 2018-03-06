package com.onek.resourcestest;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.onek.resource.PasswordModify;

public class PasswordModifyTest {

	@Test
	public void testEmptyConstructor() {
		new PasswordModify();
	}
	
	@Test
	public void testGetter() {
		PasswordModify pm = new PasswordModify();
		assertNull(pm.getLogin());
		assertNull(pm.getNewPassword());
		assertNull(pm.getOldPassword());
	}
	
}
