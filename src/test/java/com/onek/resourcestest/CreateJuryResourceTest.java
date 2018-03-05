package com.onek.resourcestest;

import static org.junit.Assert.assertNull;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.onek.resource.CreateJuryResource;

public class CreateJuryResourceTest {

	@Test
	public void testEmptyConstructor() {
		new CreateJuryResource();
	}
	
	@Test
	public void validGetter() throws IntrospectionException {
		CreateJuryResource cjr = new CreateJuryResource();
		assertNull(cjr.getFirstname());
		assertNull(cjr.getLastname());
		assertNull(cjr.getLogin());
		assertNull(cjr.getMail());
		assertNull(cjr.getPassword());
	}
}
