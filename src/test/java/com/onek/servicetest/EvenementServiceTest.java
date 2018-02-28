package com.onek.servicetest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.model.Evenement;
import com.onek.service.EvenementService;
import com.onek.utils.Password;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EvenementServiceTest {

	@Autowired
	private EvenementService evenementService;
	
	private String generateCode(Evenement event) {
		Password pass = new Password();
		Integer id = event.getIdevent();
		int length = (int) (Math.log10(id) + 1);
		String codeEvent = id + pass.generateCode(10 - length);
		return codeEvent;
	}

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
	public void testfindByCodeNull() {
		evenementService.findByCode(null);
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
	
	@Test
	public void testfindByCodeKO() {
		assertNull(evenementService.findByCode("aazazaza")); //Bad Code 
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
	@Test
	public void testfindByCodeOK() {
		assertNotNull(evenementService.findByCode("1013902701")); //Good Code 
	}
	
	@Test
	public void testaddDuplicatedEventOK() {
		Evenement event = evenementService.findById(1);
		event.setCode(generateCode(event));
		evenementService.addEvenement(event);
		List<Evenement> events = evenementService.findAll();
		for(Evenement evenement : events) {
			if(event.getNom().equals(evenement.getNom())){
				assertTrue(true);
				return;
			}
		}
		assertTrue(false);
	}
	
	@Test
	public void testfindByIdUserOK() {
		assertTrue(evenementService.myListEvents(3).size() > 0);
	}


}
