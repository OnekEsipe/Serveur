package com.onek.utilstest;

import org.junit.Test;

import com.onek.utils.Navigation;

public class NavigationTest {

	@Test(expected=NullPointerException.class)
	public void testsNotNull() {
		Navigation.redirect(null);
		Navigation.getURLParameter(null);
	}

}
