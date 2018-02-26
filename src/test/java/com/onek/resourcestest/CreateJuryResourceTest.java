package com.onek.resourcestest;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.onek.beanstest.JavaBeanTester;
import com.onek.resource.CreateJuryResource;

public class CreateJuryResourceTest {

	@Test
	public void testEmptyConstructor() {
		new CreateJuryResource();
	}
	
	@Test
	public void validGetter() throws IntrospectionException {
		JavaBeanTester.test(CreateJuryResource.class);
	}
}
