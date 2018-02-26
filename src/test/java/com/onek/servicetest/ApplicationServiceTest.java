package com.onek.servicetest;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.resource.CodeEvenementResource;
import com.onek.resource.CreateJuryResource;
import com.onek.resource.EvaluationResource;
import com.onek.resource.PasswordModify;
import com.onek.service.ApplicationService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationServiceTest {

	@Autowired
	private ApplicationService applicationService;

/*
	private Utilisateur user;
	private List<Utilisateur> users = new ArrayList<>();
	private String[] login = {"aa","bb","cc","dd","ee"};
	private String formatMail = "@gmail.com";
	
	@Before
	public void setup() {
		users = userService.getAllUsers();
		user = users.get(0);
	}
	
	@After
	public void end() {
		users = null;
		user = null;
	}
	*/
	/*
	 * TESTS DES ARGUMENTS
	 */
	
	@Test(expected=NullPointerException.class)
	public void testexportNull1() {
		applicationService.export(null,"login");
	}

	@Test(expected=NullPointerException.class)
	public void testexportNull2() {
		applicationService.export("idevent",null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testaccountNull() {
		applicationService.account(null);
	}
	
	@Test
	public void testimportEvaluationNull() {
		 assertFalse(applicationService.importEvaluation(null));
	}
	
	@Test(expected=NullPointerException.class)
	public void testimportEvaluation() {
		applicationService.importEvaluation(new EvaluationResource());
	}
	
	@Test(expected=NullPointerException.class)
	public void testcreateJuryNull() {
		applicationService.createJury(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testcreateJuryNull2() {
		applicationService.createJury(new CreateJuryResource() );
	}
	
	@Test(expected=NullPointerException.class)
	public void testsubscribeNull() {
		applicationService.subscribe(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testsubscribeNull2() {
		applicationService.subscribe(new CodeEvenementResource());
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testchangePasswordNull() {
		applicationService.changePassword(null);
	}
	
	@Test(expected=NullPointerException.class)
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

	

}