package com.onek.servicetest;

import static org.junit.Assert.*;

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
import com.onek.service.UserService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;

	private Utilisateur user;
	private List<Utilisateur> users = new ArrayList<>();
	private String[] login = {"aa","bb","cc","dd","ee","ff","gg","hh"};
	private String[] passwords = {"aa","bb","cc","dd","ee","ff","gg","hh"};
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
	
	@Test(expected=IllegalArgumentException.class)
	public void testgetAllUsersExceptCurrentInvalidId() {
		userService.getAllUsersExceptCurrent(-1);
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
	
	@Test(expected=NullPointerException.class)
	public void testeditUserNull() {
		userService.editUser(null);
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
		assertNull(userService.findUserById(Integer.MAX_VALUE));
	}
	
	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * TODO
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
		assertEquals(logins.size(), users.size());
	}
	
	@Test
	public void testuserExistOK() {
		users.forEach(user -> assertTrue(userService.userExist(user.getLogin())) );
	}

	@Test
	public void testgetAllUsersExceptCurrentOK() {
		List<Utilisateur> usersExcepted0 = userService.getAllUsersExceptCurrent(Integer.MAX_VALUE);
		assertNotNull(usersExcepted0);
		assertEquals(users.size(), usersExcepted0.size());
	}

	@Test
	public void testuserExistAndCorrectPasswordOK() {
		assertTrue(userService.userExistAndCorrectPassword("aa","aa"));
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


}
