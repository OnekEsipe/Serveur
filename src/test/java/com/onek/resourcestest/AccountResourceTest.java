package com.onek.resourcestest;

import java.util.ArrayList;

import org.junit.Test;

import com.onek.model.Utilisateur;
import com.onek.resource.AccountResource;

public class AccountResourceTest {

	@Test(expected=NullPointerException.class)
	public void testWithNullUser() {
		new AccountResource(null, new ArrayList<Integer>());
	}
	
	@Test(expected=NullPointerException.class)
	public void testWithNullList() {
		new AccountResource(new Utilisateur(), null);
	}
	
	@Test
	public void testOK() {
		new AccountResource(new Utilisateur(), new ArrayList<Integer>());
	}
}
