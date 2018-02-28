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
import com.onek.service.EventAccueilService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EventAccueilServiceTest {

	@Autowired
	private EvenementService evenementService;
	
	@Autowired
	private EventAccueilService eventAccueilService;
	

	/*
	 * TESTS DES ARGUMENTS
	 */
	@Test(expected=NullPointerException.class)
	public void testeditEvenementNull() {
		eventAccueilService.editEvenement(null);
	}

	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */
	
	@Test
	public void testeditEvenementOK() {
		Evenement event = evenementService.findById(1);
		Date datestart = new Date();
		event.setDatestart(datestart);
		eventAccueilService.editEvenement(event);
		assertEquals(datestart.getTime(),evenementService.findById(1).getDatestart().getTime());
	}

}
