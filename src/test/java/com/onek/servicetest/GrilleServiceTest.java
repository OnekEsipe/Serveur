package com.onek.servicetest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evenement;
import com.onek.service.EvenementService;
import com.onek.service.GrilleService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GrilleServiceTest {

	@Autowired
	private GrilleService grilleService;

	@Autowired
	private EvenementService evenementService;

	private int lastId = 2; //dernier id critere dans la base;


	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=NullPointerException.class)
	public void testaddCriteresNull() {
		grilleService.addCriteres(null);
	}

	@Test(expected=IllegalStateException.class)
	public void testaddCriteresEmptyList() {
		grilleService.addCriteres(new ArrayList<Critere>());
	}

	@Test(expected=NullPointerException.class)
	public void testaddCritereNull() {
		grilleService.addCritere(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testgetCritereById() {
		grilleService.getCritereById(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testsupprimerCritere() {
		grilleService.supprimerCritere(-1);
	}

	@Test(expected=NullPointerException.class)
	public void testupdateCritereNull() {
		grilleService.updateCritere(null);
	}
	@Test(expected=NullPointerException.class)
	public void testsupprimerDescripteurNull() {
		grilleService.supprimerDescripteur(null);
	}

	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */
	@Test
	public void testgetCritereByIdKO() {
		assertNull(grilleService.getCritereById(Integer.MAX_VALUE));
	}

	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */

	@Test
	public void testfindByIdOK() {
		assertTrue("critere1".equals(grilleService.getCritereById(1).getTexte()));
	}

	@Test
	public void testaddCritereOK() {
		Evenement event1 = evenementService.findById(1);
		assertNotNull(event1);
		Critere newCritere = new Critere();
		newCritere.setCategorie("Test");
		newCritere.setEvenement(event1);
		newCritere.setTexte("test de critere");
		newCritere.setCoefficient(BigDecimal.ONE);
		newCritere.setDescripteurs(new ArrayList<>());
		grilleService.addCritere(newCritere); 
		Critere critereBis = grilleService.getCritereById(++lastId); 
		assertNotNull(critereBis);
	}

	@Test
	public void testaddCriteresOK() {
		Evenement event1 = evenementService.findById(1);
		assertNotNull(event1);
		ArrayList<Critere> criteres = new ArrayList<>();
		int nb = 10;
		for(int i =0; i < nb;i++) {
			Critere newCritere = new Critere();
			newCritere.setCategorie("Test");
			newCritere.setEvenement(event1);
			newCritere.setTexte("test de critere "+(i+1));
			newCritere.setCoefficient(BigDecimal.ONE);
			newCritere.setDescripteurs(grilleService.getCritereById(1).getDescripteurs());
			criteres.add(newCritere);
		}
		grilleService.addCriteres(criteres); 
		for(int i = lastId + 1; i <= nb ; i++) {
			assertNotNull(grilleService.getCritereById(i));
		}
		lastId += nb; 
	}

	@Test
	public void testsupprimerCritereOK() {
		assertNotNull(grilleService.getCritereById(4));
		grilleService.supprimerCritere(4);
		assertNull(grilleService.getCritereById(4));
	}

	@Test
	public void testupdateCritereOK() {
		Critere critere = grilleService.getCritereById(2);
		String modif = "critere2modifie";
		assertNotNull(critere);
		critere.setTexte(modif);
		grilleService.updateCritere(critere);
		assertTrue(grilleService.getCritereById(2).getTexte().equals(modif));

	}

	@Test
	public void testsupprimerDescripteur() {
		List<Descripteur> descripteurs = grilleService.getCritereById(1).getDescripteurs();
		int nbDescripteurExpected = descripteurs.size() - 1;
		grilleService.supprimerDescripteur(descripteurs.get(0));
		assertTrue(nbDescripteurExpected == grilleService.getCritereById(1).getDescripteurs().size());
	}


}
