package com.onek.servicetest;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.model.Evenement;
import com.onek.service.EvenementService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EvenementServiceTest {

	@Autowired
	private EvenementService evenementService;
	

	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=IllegalArgumentException.class)
	public void testfindById() {
		evenementService.findById(-1);
	}

	
	@Test(expected=NullPointerException.class)
	public void testaddEvenementNull() {
		evenementService.addEvenement(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testeditEvenementNull() {
		evenementService.editEvenement(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testsupprimerEvent() {
		evenementService.supprimerEvent(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testmyListEvents() {
		evenementService.myListEvents(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testaddDuplicatedEventNull() {
		evenementService.addDuplicatedEvent(null);
	}

	
	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */
	
	@Test
	public void testfindByIdKO() {
		assertNull(evenementService.findById(Integer.MAX_VALUE));
	}
	
	@Test
	public void testmyListEventsKO() {
		assertTrue(evenementService.myListEvents(Integer.MAX_VALUE).isEmpty());
	}
	
	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */
	
	@Test
	public void testfindAllOK() {
		assertTrue(!evenementService.findAll().isEmpty());
	}
	
	
	@Test
	public void testaddEvenementOK() {
		Evenement eventTest = new Evenement();
		eventTest.setNom("test event");
		int nbEventsExpected = evenementService.findAll().size() + 1;
		evenementService.addEvenement(eventTest);
		assertTrue(nbEventsExpected == evenementService.findAll().size());
	}
	
	@Test
	public void testfindByIdOK() {
		Evenement event = evenementService.findById(1);
		assertTrue(event.getNom().equals("event1"));
	}
	
	@Test
	public void testeditEvenementOK() {
		Evenement event = evenementService.findById(1);
		Date datestart = new Date();
		event.setDatestart(datestart);
		evenementService.editEvenement(event);
		assertEquals(datestart.getTime(),evenementService.findById(1).getDatestart().getTime());
	}
	
	@Test
	public void testsupprimerEventOK() {
		evenementService.supprimerEvent(1);
		assertTrue(evenementService.findById(1).getIsdeleted());
	}


}
