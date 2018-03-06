package com.onek.utilstest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.onek.utils.Password;

public class PasswordTest {

	@Test(expected = IllegalArgumentException.class)
	public void testsArguments() {
		Password.generateCode(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testsArguments2() {
		Password.generatePassword(-1);
	}

	@Test
	public void testsResults() {
		assertNotNull(Password.generateCode(10));
		assertNotNull(Password.generatePassword(10));
	}

	@Test
	public void testsPasswordRuleLength() {
		assertEquals(false, Password.verifyPasswordRule(""));
	}

	@Test
	public void testsPasswordRuleUpperCase() {
		assertEquals(false, Password.verifyPasswordRule("azerty"));
	}

	@Test
	public void testsPasswordRules() {
		assertEquals(false, Password.verifyPasswordRule("aZert"));
		assertEquals(true, Password.verifyPasswordRule("aZerty"));
	}

}
