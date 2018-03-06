package com.onek.resourcestest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.onek.model.Descripteur;
import com.onek.resource.DescripteurResource;

public class DescripteurResourceTest {

	@Test
	public void testEmptyConstructor() {
		new DescripteurResource();
	}
	
	@Test
	public void testConstructor() {	
		new DescripteurResource(new Descripteur());
	}
	
	@Test
	public void validGetter() {
		Descripteur d = new Descripteur();
		d.setNiveau("A");
		d.setTexte("texte");
		DescripteurResource dr = new DescripteurResource(d);
		assertEquals("A", dr.getNiveau());
		assertEquals("texte", dr.getTexte());
	}
}
