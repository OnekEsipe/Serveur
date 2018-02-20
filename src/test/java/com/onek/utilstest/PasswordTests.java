package com.onek.utilstest;

import static org.junit.Assert.*;

import org.junit.Test;
import com.onek.utils.Password;

public class PasswordTests {

	@Test(expected=IllegalArgumentException.class)
	public void testsArguments() {
		Password passwordGenerator = new Password();
		passwordGenerator.generateCode(-1);
		passwordGenerator.generatePassword(-1);
	}
	
	@Test
	public void testsResults() {
		Password passwordGenerator = new Password();
		assertNotNull(passwordGenerator.generateCode(10));
		assertNotNull(passwordGenerator.generatePassword(10));
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
