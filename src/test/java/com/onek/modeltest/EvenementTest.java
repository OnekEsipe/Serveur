package com.onek.modeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.model.Evenement;

public class EvenementTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getIdTest() {
		Evenement evenement = Mockito.mock(Evenement.class);

		Mockito.when(evenement.getIdevent()).thenReturn(1);

		assertEquals(1, evenement.getIdevent().intValue());
	}
	
	@Test
	public void codeTest() {
		Evenement evenement = Mockito.mock(Evenement.class);
		Mockito.when(evenement.getCode()).thenCallRealMethod();

		assertEquals(null, evenement.getCode());

		Mockito.doCallRealMethod().when(evenement).setCode(Mockito.anyString());

		evenement.setCode("12348");
		assertEquals("12348", evenement.getCode());
	}
	
	@Test
	public void isopenedTest() {
		Evenement evenement = Mockito.mock(Evenement.class);
		Mockito.when(evenement.getIsopened()).thenCallRealMethod();

		assertEquals(null, evenement.getIsopened());

		Mockito.doCallRealMethod().when(evenement).setIsopened(Mockito.anyBoolean());

		evenement.setIsopened(true);
		assertEquals(true , evenement.getIsopened());
	}
	
	@Test
	public void issignedTest() {
		Evenement evenement = Mockito.mock(Evenement.class);
		Mockito.when(evenement.getSigningneeded()).thenCallRealMethod();

		assertEquals(null, evenement.getSigningneeded());

		Mockito.doCallRealMethod().when(evenement).setSigningneeded(Mockito.anyBoolean());

		evenement.setSigningneeded(true);
		assertEquals(true , evenement.getSigningneeded());
	}
	
	@Test
	public void nomTest() {
		Evenement evenement = Mockito.mock(Evenement.class);
		Mockito.when(evenement.getNom()).thenCallRealMethod();

		assertEquals(null, evenement.getNom());

		Mockito.doCallRealMethod().when(evenement).setNom(Mockito.anyString());

		evenement.setNom("Soutenance 1ere année");
		assertEquals("Soutenance 1ere année", evenement.getNom());
	}
	
	@Test
	public void statusTest() {
		Evenement evenement = Mockito.mock(Evenement.class);
		Mockito.when(evenement.getStatus()).thenCallRealMethod();

		assertEquals(null, evenement.getStatus());

		Mockito.doCallRealMethod().when(evenement).setStatus(Mockito.anyString());

		evenement.setStatus("Ouvert");
		assertEquals("Ouvert", evenement.getStatus());
	}


}
