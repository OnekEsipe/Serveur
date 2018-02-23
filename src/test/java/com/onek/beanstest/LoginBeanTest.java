package com.onek.beanstest;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.managedbean.LoginBean;

public class LoginBeanTest {
	LoginBean loginBean;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		loginBean = Mockito.mock(LoginBean.class);
	}

	@Test
	public void invalidUser() {

		Mockito.when(loginBean.getLogin()).thenCallRealMethod();
		Mockito.when(loginBean.getMotDePasse()).thenCallRealMethod();

		Mockito.doCallRealMethod().when(loginBean).setLogin(Mockito.anyString());
		Mockito.doCallRealMethod().when(loginBean).setMotDePasse(Mockito.anyString());
		//		Mockito.doCallRealMethod().when(userService).authentification(Mockito.anyString(), Mockito.anyString());
		//Mockito.doCallRealMethod().when(loginBean).setMotDePasse(Mockito.anyString());


		loginBean.setLogin("aa");
		loginBean.setMotDePasse("aa");
		assertEquals("aa", loginBean.getLogin());
		assertEquals("aa", loginBean.getMotDePasse());

	}
	/*
	@Test
	public void validUser() {
		loginBean.setLogin("aa");
		loginBean.setMotDePasse("aa");
		Mockito.when(loginBean.getLogin()).thenCallRealMethod();
		assertEquals("aa", loginBean.getLogin());
	}
	 */


}
