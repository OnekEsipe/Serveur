package com.onek.modeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.model.Candidat;

public class ModelTest {

	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void candidatTest() {
		Candidat candidat = Mockito.mock(Candidat.class);		 
		Mockito.when(candidat.getNom()).thenCallRealMethod();
		 
		assertEquals(null, candidat.getNom());
		 
		// on rétablit également le comportement de la méthode setLogin()
		Mockito.doCallRealMethod().when(candidat).setNom(Mockito.anyString());
		 
		user.setLogin("drake");
		System.out.println(user.getLogin()); // affiche enfin "drake" !
	}

}
