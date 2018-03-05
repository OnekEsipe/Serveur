package com.onek.resourcestest;

import static org.junit.Assert.assertNull;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.onek.resource.CodeEvenementResource;

public class CodeEvenementResourceTest {

	@Test
	public void testEmptyConstructor() {
		new CodeEvenementResource();
	}
	
	@Test
	public void validGetter() throws IntrospectionException {
		CodeEvenementResource cer = new CodeEvenementResource();
		assertNull(cer.getLogin());
		assertNull(cer.getEventCode());
	}
}
