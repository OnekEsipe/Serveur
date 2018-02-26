package com.onek.resourcestest;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.onek.beanstest.JavaBeanTester;
import com.onek.resource.CodeEvenementResource;

public class CodeEvenementResourceTest {

	@Test
	public void testEmptyConstructor() {
		new CodeEvenementResource();
	}
	
	@Test
	public void validGetter() throws IntrospectionException {
		JavaBeanTester.test(CodeEvenementResource.class);
	}
}
