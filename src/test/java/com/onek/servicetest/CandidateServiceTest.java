package com.onek.servicetest;

import static org.junit.Assert.*;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.model.Candidat;
import com.onek.model.Evenement;
import com.onek.service.CandidateService;
import com.onek.service.EvenementService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CandidateServiceTest {

	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private EvenementService event;
	
	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=IllegalArgumentException.class)
	public void testfindCandidatesByEvent() {
		candidateService.findCandidatesByEvent(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsupprimerCandidat() {
		candidateService.supprimerCandidat(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindCandidatesById() {
		candidateService.findCandidatesById(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testaddCandidateNull() {
		candidateService.addCandidate(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testaddCandidatesNull() {
		candidateService.addCandidates(null);
	}

	@Test(expected=IllegalStateException.class)
	public void testNotEmptyList() {
		List<Candidat> emptyList = new ArrayList<>();
		candidateService.addCandidates(emptyList);
	}

	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */
	
	@Test
	public void testfindCandidatesByEventKO() {
		assertTrue(candidateService.findCandidatesByEvent(Integer.MAX_VALUE).size() == 0); //idevent inexistant
	}
	
	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */
	@Test
	public void testaddCandidateOK() {
		Evenement event1 = event.findById(1);
		Candidat candidat = new Candidat();
		candidat.setNom("candidat");
		candidat.setEvenement(event1);
	
		int tailleactuelle = candidateService.findCandidatesByEvent(1).size();
		candidateService.addCandidate(candidat);
		assertEquals(tailleactuelle + 1,candidateService.findCandidatesByEvent(1).size() );

	}
	
	@Test
	public void testfindCandidatesByIdOK() {
		assertNotNull(candidateService.findCandidatesById(1));
	}
	
	@Test
	public void testaddCandidatesOK() {
		Evenement event1 = event.findById(1);
		int nb = 10;
		ArrayList<Candidat> candidates = new ArrayList<>();
		for(int i = 0; i < nb;i++) {
			Candidat candidat = new Candidat();
			candidat.setNom("candidat"+i);
			candidat.setEvenement(event1);
			candidates.add(candidat);
		}
		int sizeOld = candidateService.findCandidatesByEvent(1).size();
		candidateService.addCandidates(candidates);
		assertEquals(sizeOld + nb, candidateService.findCandidatesByEvent(1).size());
	}
	
	@Test
	public void testfindCandidatesByEventOK() {
		assertTrue(candidateService.findCandidatesByEvent(1).size() > 0);
	}
	
	@Test
	public void testsupprimerCandidatOK() {
		candidateService.supprimerCandidat(2);
		assertEquals(null, candidateService.findCandidatesById(2));
	}
	
	
}
