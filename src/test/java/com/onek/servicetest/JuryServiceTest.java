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

import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;
import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;
import com.onek.service.EvenementService;
import com.onek.service.GrilleService;
import com.onek.service.JuryService;
import com.onek.service.UserService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JuryServiceTest {

	@Autowired
	private JuryService juryService;
	
	@Autowired
	private EvenementService eventService;
	
	@Autowired
	private UserService userService;

	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=IllegalArgumentException.class)
	public void testfindJurysByIdevent() {
		juryService.findJurysByIdevent(-1);
	}

	@Test(expected=NullPointerException.class)
	public void testassociatedJurysCandidatesByEventNull() {
		juryService.associatedJurysCandidatesByEvent(null,1);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testassociatedJurysCandidatesByEventEmptyList() {
		juryService.associatedJurysCandidatesByEvent(new ArrayList<>(),1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testassociatedJurysCandidatesByEventId() {
		List<Jury> jurys = new ArrayList<>();
		jurys.add(new Jury());
		juryService.associatedJurysCandidatesByEvent(jurys,-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testlistJurysByEvent() {
		juryService.listJurysByEvent(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testlistJurysAnnonymesByEvent() {
		juryService.listJurysAnnonymesByEvent(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testsupprimerUtilisateur() {
		juryService.supprimerUtilisateur(-1,1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testsupprimerUtilisateur2() {
		juryService.supprimerUtilisateur(1,-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testaddJuryToEvent() {
		juryService.addJuryToEvent(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testaddJuryToEventBadIdevent() {
		Jury jury = new Jury();
		Evenement event = new Evenement();
		event.setIdevent(-1);
		Utilisateur user = new Utilisateur();
		user.setIduser(1);
		jury.setEvenement(event);
		jury.setUtilisateur(user);
		juryService.addJuryToEvent(jury);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testaddJuryToEventBadIduser() {
		Jury jury = new Jury();
		Evenement event = new Evenement();
		event.setIdevent(1);
		Utilisateur user = new Utilisateur();
		user.setIduser(-1);
		jury.setEvenement(event);
		jury.setUtilisateur(user);
		juryService.addJuryToEvent(jury);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindById() {
		juryService.findById(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindJuryById() {
		juryService.findById(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testsupprimerUtilisateurAnonyme() {
		juryService.supprimerUtilisateurAnonyme(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testaddListJurys() {
		juryService.addListJurys(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testaddListJurysEmptyList() {
		juryService.addListJurys(new ArrayList<>());
	}

	@Test(expected=NullPointerException.class)
	public void testfindJuryAndAnonymousByIdEventNull() {
		juryService.findJuryAndAnonymousByIdEvent(1,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindJuryAndAnonymousByIdEvent() {
		juryService.findJuryAndAnonymousByIdEvent(-1,"login");
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testfindByUserNull() {
		juryService.findByUser(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindJurysAnnonymesByEvent() {
		juryService.findJurysAnnonymesByEvent(-1);
	}
	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */
	@Test
	public void testgetCritereByIdKO() {
		assertTrue(juryService.findJurysByIdevent(Integer.MAX_VALUE).isEmpty());
	}
	
	@Test
	public void testfindByUserKO() {
		assertTrue(juryService.findByUser(userService.findUserById(4)).isEmpty());
	}
	
	@Test
	public void testfindJuryAndAnonymousByIdEventKO1() {
		assertTrue(juryService.findJuryAndAnonymousByIdEvent(1,"a").isEmpty());
	}
	
	@Test
	public void testfindJuryAndAnonymousByIdEventKO2() {
		assertTrue(juryService.findJuryAndAnonymousByIdEvent(Integer.MAX_VALUE,"aa").isEmpty());
	}
	
	@Test
	public void testfindJurysAnnonymesByEventKO() {
		assertTrue(juryService.findJurysAnnonymesByEvent(Integer.MAX_VALUE).isEmpty());
	}

	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */
	@Test
	public void testfindJurysByIdeventOK() {
		assertTrue(juryService.findJurysByIdevent(1).size() == 3);
	}
	
	@Test
	public void testassociatedJurysCandidatesByEventOK() {
		int idevent = 1;
		List<Jury> jurys = juryService.findJurysByIdevent(idevent);
		assertTrue(!jurys.isEmpty());
		assertTrue(!juryService.associatedJurysCandidatesByEvent(jurys, idevent).isEmpty());
	}
	
	@Test
	public void testlistJurysByEventOK() {
		assertTrue(!juryService.listJurysByEvent(1).isEmpty());
	}
	
	@Test
	public void testlistJurysAnnonymesByEventOK() {
		assertTrue(!juryService.listJurysAnnonymesByEvent(3).isEmpty());
	}
	
	@Test
	public void testfindAllJurys() {
		assertTrue(!juryService.findAllJurys().isEmpty());
	}
	
	@Test
	public void testsupprimerUtilisateurOK() {
		int nbJurysExpected = juryService.findJurysByIdevent(3).size() - 1;
		juryService.supprimerUtilisateur(12,3);
		assertTrue(nbJurysExpected == juryService.findJurysByIdevent(3).size());
	}
	
	@Test
	public void testfindByIdOK() {
		assertTrue(juryService.findById(1).getLogin().equals("aa"));
	}

	@Test
	public void testfindJuryByIdOK() {
		assertNotNull(juryService.findJuryById(1));
	}
	
	@Test
	public void testsupprimerUtilisateurAnonymeOK() {
		assertNotNull(juryService.findById(5));
		juryService.supprimerUtilisateurAnonyme(5);
		assertNull(juryService.findById(5));
	}
	
	@Test
	public void testaddListJurysOK() {
		int nbJurysAdd = 2;
		int nbJurysExpected = juryService.findJurysByIdevent(1).size() + nbJurysAdd;
		
		Jury jury1 = new Jury();
		jury1.setEvenement(eventService.findById(1));
		jury1.setUtilisateur(userService.findUserById(6));
		
		Jury jury2 = new Jury();
		jury2.setEvenement(eventService.findById(1));
		jury2.setUtilisateur(userService.findUserById(7));
		
		ArrayList<Jury> jurys = new ArrayList<>();
		jurys.add(jury1);
		jurys.add(jury2);
		juryService.addListJurys(jurys);
		
		assertTrue(nbJurysExpected == juryService.findJurysByIdevent(1).size());
	}
	
	@Test
	public void testaddJuryToEventOK1() {
		List<Jury> jurysEvent1 = juryService.findJurysByIdevent(1);
		int nbJurysEvent1 = jurysEvent1.size();
		juryService.addJuryToEvent(jurysEvent1.get(0)); //Echec car l'utilisateur deja associé à l'evenement
		assertTrue(nbJurysEvent1 == juryService.findJurysByIdevent(1).size());

	}
	
	@Test
	public void testaddJuryToEventOK2() {
		int nbJurysEvent2Expected = juryService.findJurysByIdevent(1).size() + 1;
		Jury newJury = new Jury();
		newJury.setEvenement(eventService.findById(1));
		newJury.setUtilisateur(userService.findUserById(4));
		juryService.addJuryToEvent(newJury);
		assertTrue(nbJurysEvent2Expected == juryService.findJurysByIdevent(1).size());
	}
	
	@Test
	public void testfindByUserOK() {
		assertTrue(!juryService.findByUser(userService.findUserById(1)).isEmpty());
	}
	
	@Test
	public void testfindJuryAndAnonymousByIdEventOK() {
		assertTrue(!juryService.findJuryAndAnonymousByIdEvent(1,"aa").isEmpty());
	}
	
	@Test
	public void testfindJurysAnnonymesByEventOK() {
		assertTrue(!juryService.findJurysAnnonymesByEvent(2).isEmpty());
	}


}
