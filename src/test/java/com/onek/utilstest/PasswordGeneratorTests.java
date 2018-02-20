package com.onek.utilstest;

import static org.junit.Assert.*;


import org.junit.Test;
import com.onek.utils.PasswordGenerator;

public class PasswordGeneratorTests {

	@Test(expected=IllegalArgumentException.class)
	public void testsArguments() {
		PasswordGenerator passwordGenerator = new PasswordGenerator();
		passwordGenerator.generateCode(-1);
		passwordGenerator.generatePassword(-1);
	}
	
	@Test
	public void testsResults() {
		PasswordGenerator passwordGenerator = new PasswordGenerator();
		assertNotNull(passwordGenerator.generateCode(10));
		assertNotNull(passwordGenerator.generatePassword(10));
	}

}
