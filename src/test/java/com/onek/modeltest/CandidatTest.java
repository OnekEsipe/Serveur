package com.onek.modeltest;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.onek.model.Candidat;
import com.onek.model.Evenement;


public class CandidatTest {


	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void nomTest() {
		Candidat candidat = Mockito.mock(Candidat.class);		 
		Mockito.when(candidat.getNom()).thenCallRealMethod();
		 
		assertEquals(null, candidat.getNom());
		 
		Mockito.doCallRealMethod().when(candidat).setNom(Mockito.anyString());
		 
		candidat.setNom("onek");
		assertEquals("onek", candidat.getNom());
		candidat.setNom("@OnEk3!é");
		assertEquals("@OnEk3!é", candidat.getNom());
	}
	
	@Test
	public void prenomTest() {
		Candidat candidat = Mockito.mock(Candidat.class);		 
		Mockito.when(candidat.getPrenom()).thenCallRealMethod();
		 
		assertEquals(null, candidat.getPrenom());
		 
		Mockito.doCallRealMethod().when(candidat).setPrenom(Mockito.anyString());
		 
		candidat.setPrenom("test");
		assertEquals("test", candidat.getPrenom());
		candidat.setPrenom("!T3_st$");
		assertEquals("!T3_st$", candidat.getPrenom());
	}
	
	@Test
	public void getIdTest() {
		Candidat candidat = Mockito.mock(Candidat.class);		 
		 
		Mockito.when(candidat.getIdcandidat()).thenReturn(1);

		assertEquals(1, candidat.getIdcandidat().intValue());
	}
	

	@Test
	public void getEvenementTest() {
		Candidat candidat = Mockito.mock(Candidat.class);		 
		Mockito.when(candidat.getEvenement()).thenCallRealMethod();
		
		assertEquals(null, candidat.getEvenement());
		Mockito.when(candidat.getEvenement()).thenReturn(new Evenement());
		assertNotNull(candidat.getEvenement());
	}
	
	@Test
	public void candidatTest() {
		Candidat candidat = Mockito.mock(Candidat.class);		
		
		assertNotNull(candidat);
	}
	
}
