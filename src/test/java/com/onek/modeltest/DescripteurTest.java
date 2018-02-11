package com.onek.modeltest;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.model.Descripteur;

public class DescripteurTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getIdTest() {
		Descripteur descripteur = Mockito.mock(Descripteur.class);

		Mockito.when(descripteur.getIddescripteur()).thenReturn(1);

		assertEquals(1, descripteur.getIddescripteur().intValue());
	}

	@Test
	public void niveauTest() {
		Descripteur descripteur = Mockito.mock(Descripteur.class);
		Mockito.when(descripteur.getNiveau()).thenCallRealMethod();

		assertEquals(null, descripteur.getNiveau());

		Mockito.doCallRealMethod().when(descripteur).setNiveau(Mockito.anyString());

		descripteur.setNiveau("A");
		assertEquals("A", descripteur.getNiveau());
	}

	@Test
	public void poidsTest() {
		Descripteur descripteur = Mockito.mock(Descripteur.class);

		Mockito.when(descripteur.getPoids()).thenReturn(new BigDecimal(1));

		assertEquals(1, descripteur.getPoids().intValue());
	}
	
	@Test
	public void texteTest() {
		Descripteur descripteur = Mockito.mock(Descripteur.class);	 
		Mockito.when(descripteur.getTexte()).thenCallRealMethod();
		 
		assertEquals(null, descripteur.getTexte());
		 
		Mockito.doCallRealMethod().when(descripteur).setTexte(Mockito.anyString());
		 
		descripteur.setTexte("descr1");
		assertEquals("descr1", descripteur.getTexte());
		
		descripteur.setTexte("fhezfnznvjbiebbv ezbzbzefeza jefzaf 4er68g 4erg5er68g4 e1ger1gegr7z 4g6ag ngro hoerg1&");
		assertEquals("fhezfnznvjbiebbv ezbzbzefeza jefzaf 4er68g 4erg5er68g4 e1ger1gegr7z 4g6ag ngro hoerg1&", descripteur.getTexte());
	}
}
