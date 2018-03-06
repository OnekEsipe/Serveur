package com.onek.resourcestest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.onek.model.Utilisateur;
import com.onek.resource.LoginResource;

public class LoginResourceTest {

	@Test
	public void testEmptyConstructor() {
		new LoginResource();
	}
	
	@Test
	public void testConstructor() {
		new LoginResource(createUser());
	}
	
	@Test
	public void testGetter() {
		LoginResource lr = new LoginResource(createUser());
		assertEquals("toto", lr.getLogin());
		assertEquals("hash", lr.getPassword());
	}
	
	private Utilisateur createUser() {
		Utilisateur user = new Utilisateur();
		user.setLogin("toto");
		user.setMotdepasse("hash");
		return user;
	}
}
