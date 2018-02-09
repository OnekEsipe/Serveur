package com.onek.modeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.model.Critere;

public class CritereTest {

	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getIdTest() {
		Critere critere = Mockito.mock(Critere.class);		 
		 
		Mockito.when(critere.getIdcritere()).thenReturn(1);

		assertEquals(1, critere.getIdcritere().intValue());
	}
	
	@Test
	public void categorieTest() {
		Critere critere = Mockito.mock(Critere.class);		 
		Mockito.when(critere.getCategorie()).thenCallRealMethod();
		 
		assertEquals(null, critere.getCategorie());
		 
		Mockito.doCallRealMethod().when(critere).setCategorie(Mockito.anyString());
		 
		critere.setCategorie("cat1");
		assertEquals("cat1", critere.getCategorie());
	}
	
	@Test
	public void coefficientTest() {
		Critere critere = Mockito.mock(Critere.class);		 
		 
		Mockito.when(critere.getCoefficient()).thenReturn(1);

		assertEquals(1, critere.getCoefficient().intValue());
	}
	
	@Test
	public void texteTest() {
		Critere critere = Mockito.mock(Critere.class);		 
		Mockito.when(critere.getTexte()).thenCallRealMethod();
		 
		assertEquals(null, critere.getTexte());
		 
		Mockito.doCallRealMethod().when(critere).setTexte(Mockito.anyString());
		 
		critere.setTexte("cat1");
		assertEquals("cat1", critere.getTexte());
		
		critere.setTexte("fhezfnznvjbiebbv ezbzbzefeza jefzaf 4er68g 4erg5er68g4 e1ger1gegr7z 4g6ag ngro hoerg1&");
		assertEquals("fhezfnznvjbiebbv ezbzbzefeza jefzaf 4er68g 4erg5er68g4 e1ger1gegr7z 4g6ag ngro hoerg1&", critere.getTexte());
	}

}
