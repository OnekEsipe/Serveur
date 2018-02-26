package com.onek.utilstest;

import static org.junit.Assert.*;

import org.junit.Test;
import com.onek.utils.Password;

public class PasswordTest {

	@Test(expected=IllegalArgumentException.class)
	public void testsArguments() {
		Password passwordGenerator = new Password();
		passwordGenerator.generateCode(-1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testsArguments2() {
		Password passwordGenerator = new Password();
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
