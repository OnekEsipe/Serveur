package com.onek.servicetest;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.model.Evenement;
import com.onek.model.Utilisateur;
import com.onek.service.EvenementService;
import com.onek.service.UserService;
import com.onek.utils.Encode;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EvenementService eventService;

	private Utilisateur user;
	private List<Utilisateur> users = new ArrayList<>();
	private String[] login = {"aa","bb","cc","dd"};
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
	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=NullPointerException.class)
	public void testfindByLoginNull() {
		userService.findByLogin(null);
	}

	@Test(expected=NullPointerException.class)
	public void testuserExistNull() {
		userService.userExist(null);
	}

	@Test(expected=NullPointerException.class)
	public void testupdateUserInfosNull() {
		userService.updateUserInfos(null);
	}

	@Test(expected=NullPointerException.class)
	public void testaddJurysAnonymesNull1() {
		userService.addJurysAnonymes(null, new Evenement());
	}

	@Test(expected=NullPointerException.class)
	public void testaddJurysAnonymesNull2() {
		userService.addJurysAnonymes(new ArrayList<>(), null);
	}

	@Test(expected=IllegalStateException.class)
	public void testaddJurysAnonymesEmptyList() {
		userService.addJurysAnonymes(new ArrayList<>(), new Evenement());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testdeleteUserInvalidId() {
		userService.deleteUser(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testaddUserNull() {
		userService.addUser(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testaddUserLoginExist() {
		userService.addUser(user);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testaddUserMailExist() {
		userService.addUser(user);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testgetAllUsersExceptCurrentInvalidId() {
		userService.getAllUsersExceptCurrentAndAnonymous(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testuserExistAndCorrectPasswordNull1() {
		userService.userExistAndCorrectPassword(null,"aa");
	}

	@Test(expected=NullPointerException.class)
	public void testuserExistAndCorrectPasswordNull2() {
		userService.userExistAndCorrectPassword("aa",null);
	}

	@Test(expected=NullPointerException.class)
	public void testauthentificationNull1() {
		userService.authentification(null,"aa");
	}

	@Test(expected=NullPointerException.class)
	public void testauthentificationNull2() {
		userService.authentification("aa",null);
	}

	@Test(expected=NullPointerException.class)
	public void testmailExistNull() {
		userService.mailExist(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testfindByMailNull() {
		userService.findByMail(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindUserByIdInvalidId() {
		userService.findUserById(-1);
	}
	
	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */
	@Test
	public void testFindByLoginKO() {
		assertNull(userService.findByLogin("a"));
	}
	
	@Test
	public void testuserExistKO() {
		assertFalse(userService.userExist("a"));
	}
	
	@Test
	public void testuserExistAndCorrectPasswordKO1() {
		assertFalse(userService.userExistAndCorrectPassword("a","a"));
	}
	
	@Test
	public void testuserExistAndCorrectPasswordKO2() {
		assertFalse(userService.userExistAndCorrectPassword("aa","a"));
	}
	
	@Test
	public void testuserExistAndCorrectPasswordKO3() {
		assertFalse(userService.userExistAndCorrectPassword("a","aa"));
	}
	
	@Test
	public void testuserExistAndCorrectPasswordKO4() {
		assertFalse(userService.userExistAndCorrectPassword("aa",""));
	}
	
	@Test
	public void testauthentificationKO1() {
		assertFalse(userService.authentification("a","a"));
	}
	
	@Test
	public void testauthentificationKO2() {
		assertFalse(userService.authentification("aa","a"));
	}
	
	@Test
	public void testauthentificationKO3() {
		assertFalse(userService.authentification("a","aa"));
	}
	
	@Test
	public void testmailExistKO() {
		assertFalse(userService.mailExist("a@gmail.com"));
	}
	
	@Test
	public void testfindByMailKO() {
		assertNull(userService.findByMail("a@gmail.com"));
	}

	@Test(expected=NoResultException.class)
	public void testfindUserByIdKO() {
		userService.findUserById(Integer.MAX_VALUE);
	}
	
	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */

	@Test
	public void testFindByLoginOK() {
		List<String> logins = Arrays.asList(login);
		List<Utilisateur> users = new ArrayList<>();
		logins.forEach(login ->{
			Utilisateur user = userService.findByLogin(login);
			assertNotNull(user);
			users.add(user);
		});
		System.out.println("logins size : "+logins.size()+" users size : "+users.size());
		assertTrue(logins.size() == users.size());
	}
	
	@Test
	public void testuserExistOK() {
		users.forEach(user -> assertTrue(userService.userExist(user.getLogin())) );
	}

	@Test
	public void testgetAllUsersExceptCurrentOK() {
		List<Utilisateur> usersExcepted0 = userService.getAllUsersExceptCurrentAndAnonymous(Integer.MAX_VALUE);
		assertNotNull(usersExcepted0);
		assertTrue(usersExcepted0.size() > 1);
	}

	@Test
	public void testuserExistAndCorrectPasswordOK() {
		Utilisateur user = userService.findByLogin("bb");
		assertTrue(userService.userExistAndCorrectPassword(user.getLogin(),user.getMotdepasse()));
	}
	
	@Test
	public void testuserExistAndCorrectPasswordOK2() throws NoSuchAlgorithmException, UnsupportedEncodingException {

		user.setDroits("A");
		user.setMotdepasse("a");
		userService.updateUserInfos(user);
		user = userService.findByLogin(user.getLogin());
		assertTrue(userService.userExistAndCorrectPassword(user.getLogin(),Encode.sha1("a")));
	}
	
	@Test
	public void testauthentificationOK() {
		assertTrue(userService.authentification("aa","aa"));
	}
	
	@Test
	public void testmailExistOK() {
		assertTrue(userService.mailExist(login[0]+formatMail));
	}
	
	@Test
	public void testfindByMailOK() {
		Utilisateur user = userService.findByMail(login[0]+formatMail);
		assertNotNull(user);
		assertEquals(user.getIduser().intValue(), 1);
	}

	@Test
	public void testfindUserByIdOK(){
		Utilisateur user = userService.findUserById(users.get(0).getIduser());
		assertNotNull(user);
		assertEquals(user.getIduser(), users.get(0).getIduser());
	}
	
	@Test
	public void testupdateUserInfos() {
		String login = user.getLogin();
		user.setMail((login+login.charAt(0)+formatMail));
		user.setLogin(login+login.charAt(0));
		userService.updateUserInfos(user);
		Utilisateur userupdated = userService.findUserById(user.getIduser()); //On recupère le nouvel objet mis a jour
		
		assertEquals(user.getIduser(), userupdated.getIduser());
		assertEquals(userupdated.getMail(), login+login.charAt(0)+formatMail);
		assertEquals(userupdated.getLogin(), login+login.charAt(0));	
		
		//On remet l'objet dans son etat initial
		user.setLogin(login);
		user.setMail(login+formatMail);
		userService.updateUserInfos(user);
	}
	
	@Test
	public void testaddJurysAnonymes() {
		
		List<Utilisateur> jurysAnonymes = new ArrayList<>();
		
		int debut = users.size();
		int intervalle = 10;
		int nb = debut + intervalle ;
		for(int i = debut; i < nb; i++) {
			Utilisateur user = new Utilisateur();
			user.setDroits("A");
			user.setLogin("jury"+i);
			user.setNom("jury"+i);
			user.setIsdeleted(false);
			jurysAnonymes.add(user);
		}
		
		//Faudra au moins un evenement de créé (de id=1) pour que ce test fonctionne 
		Evenement event = eventService.findById(1);
		userService.addJurysAnonymes(jurysAnonymes, event);
		assertEquals(users.size() + intervalle, userService.getAllUsers().size());
	}
	
	@Test
	public void testdeleteUser() {
		int iddeleted = user.getIduser();
		userService.deleteUser(iddeleted);
		assertTrue(userService.findUserById(iddeleted).getIsdeleted());
		
	}
	
	@Test
	public void testaddUser() {
		Utilisateur adduser = new Utilisateur();
		String label = "azerty"+users.size();
		adduser.setLogin(label);
		adduser.setMail(label+formatMail);
		adduser.setNom(label);
		adduser.setMotdepasse(label);
		userService.addUser(adduser);
		Utilisateur us = userService.findByLogin(label);
		assertNotNull(us);
		assertEquals(us.getLogin(), label);
	}
	
	@Test
	public void testgetAllUsersExceptDeletedansAnoOK() {
		assertTrue(!userService.getAllUsersExceptDeleted().isEmpty());
	}

}
