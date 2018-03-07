package com.onek.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onek.model.Evenement;
import com.onek.resource.CodeEvenementResource;
import com.onek.resource.CreateJuryResource;
import com.onek.resource.EvaluationResource;
import com.onek.resource.PasswordModify;
import com.onek.service.ApplicationService;
import com.onek.service.EvenementService;
import com.onek.utils.StatutEvenement;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationServiceTest {

	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private EvenementService evenementService;

	/*
	 * private Utilisateur user; private List<Utilisateur> users = new ArrayList<>(); private String[] login =
	 * {"aa","bb","cc","dd","ee"}; private String formatMail = "@gmail.com";
	 * 
	 * @Before public void setup() { users = userService.getAllUsers(); user = users.get(0); }
	 * 
	 * @After public void end() { users = null; user = null; }
	 */
	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected = NullPointerException.class)
	public void testexportNull1() {
		applicationService.export(null, "login");
	}

	@Test(expected = NullPointerException.class)
	public void testexportNull2() {
		applicationService.export("idevent", null);
	}

	@Test(expected = NullPointerException.class)
	public void testaccountNull() {
		applicationService.account(null);
	}

	@Test
	public void testimportEvaluationNull() {
		assertFalse(applicationService.importEvaluation(null));
	}

	@Test(expected = NullPointerException.class)
	public void testimportEvaluation() {
		applicationService.importEvaluation(new EvaluationResource());
	}

	@Test(expected = NullPointerException.class)
	public void testcreateJuryNull() {
		applicationService.createJury(null);
	}

	@Test(expected = NullPointerException.class)
	public void testcreateJuryNull2() {
		applicationService.createJury(new CreateJuryResource());
	}

	@Test(expected = NullPointerException.class)
	public void testsubscribeNull() {
		applicationService.subscribe(null);
	}

	@Test(expected = NullPointerException.class)
	public void testsubscribeNull2() {
		applicationService.subscribe(new CodeEvenementResource());
	}

	@Test(expected = NullPointerException.class)
	public void testchangePasswordNull() {
		applicationService.changePassword(null);
	}

	@Test(expected = NullPointerException.class)
	public void testchangePasswordNull2() {
		applicationService.changePassword(new PasswordModify());
	}

	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */

	@Test
	public void testexportKO1() {
		assertEquals(Optional.empty(), applicationService.export("a", "ff"));
	}

	@Test
	public void testexportKO2() {
		assertEquals(Optional.empty(), applicationService.export("1", "f"));
	}

	@Test
	public void testexportKO3() {
		assertEquals(Optional.empty(), applicationService.export("1", "ff"));
	}

	@Test
	public void testexportKO4() {
		assertEquals(Optional.empty(), applicationService.export("1", "hh"));
	}

	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */
	@Test
	public void testexportOK1() {
		assertNotNull(applicationService.export("2", "ff"));
	}

	@Test
	public void testaccount() {
		assertNotNull(applicationService.account("ff"));
	}

	/*
	 * INSCRIPTION PAR CODE
	 */

	@Test
	public void testICLoginNotExist() {
		CodeEvenementResource cer = createCodeEvenementResource("toto123", "0000000");
		assertFalse(applicationService.subscribe(cer));
	}	

	@Test
	public void testICUserDelete() {
		CodeEvenementResource cer = createCodeEvenementResource("gg", "0000000");
		assertFalse(applicationService.subscribe(cer));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testICBadCode() {
		CodeEvenementResource cer = createCodeEvenementResource("aa", "0000000");
		applicationService.subscribe(cer);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testICEventNotOpened() {
		Evenement event = evenementService.findById(1);
		event.setIsopened(false);
		evenementService.editEvenement(event);
		CodeEvenementResource cer = createCodeEvenementResource("aa", "1013902701");
		applicationService.subscribe(cer);			
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testICEventIsDeleted() {
		Evenement event = evenementService.findById(1);
		event.setIsopened(true);
		event.setIsdeleted(true);
		evenementService.editEvenement(event);
		CodeEvenementResource cer = createCodeEvenementResource("aa", "1013902701");
		applicationService.subscribe(cer);			
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testICEventIsClosed() {
		Evenement event = evenementService.findById(1);
		event.setIsopened(true);	
		event.setIsdeleted(false);	
		event.setStatus(StatutEvenement.FERME.toString());
		evenementService.editEvenement(event);
		CodeEvenementResource cer = createCodeEvenementResource("aa", "1013902701");
		applicationService.subscribe(cer);	
	}
	
	@Test(expected = IllegalStateException.class)
	public void testICJuryIsAssociated() {
		Evenement event = evenementService.findById(1);
		event.setIsopened(true);	
		event.setIsdeleted(false);	
		event.setStatus(StatutEvenement.BROUILLON.toString());
		evenementService.editEvenement(event);
		CodeEvenementResource cer = createCodeEvenementResource("aa", "1013902701");
		applicationService.subscribe(cer);		
	}
	
	@Test
	public void testICSubscribeOK() {
		Evenement event = evenementService.findById(1);
		event.setIsopened(true);	
		event.setIsdeleted(false);
		event.setStatus(StatutEvenement.BROUILLON.toString());
		evenementService.editEvenement(event);
		CodeEvenementResource cer = createCodeEvenementResource("ii", "1013902701");
		assertTrue(applicationService.subscribe(cer));			
	}

	private CodeEvenementResource createCodeEvenementResource(String login, String code) {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "{ \"Login\" : \"" + login + "\", \"Event_code\" : \"" + code + "\" }";
		try {
			return objectMapper.readValue(json, CodeEvenementResource.class);
		} catch (IOException e) {
			// ignore
		}
		return null;
	}

}