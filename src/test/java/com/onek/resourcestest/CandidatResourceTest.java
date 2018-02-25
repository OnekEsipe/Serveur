package com.onek.resourcestest;

import org.junit.Test;

import com.onek.model.Candidat;
import com.onek.resource.CandidatResource;

public class CandidatResourceTest {

	@Test(expected=NullPointerException.class)
	public void testWithNullCandidate() {
		new CandidatResource(null);
	}
	
	@Test
	public void testOK() {
		new CandidatResource(new Candidat());
	}
}
