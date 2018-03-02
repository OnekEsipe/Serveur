package com.onek.servicetest;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.model.Utilisateur;
import com.onek.service.PasswordService;
import com.onek.service.UserService;
import com.onek.utils.Encode;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordServiceTest {

	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UserService userService;
	
	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=NullPointerException.class)
	public void testresetNull() {
		passwordService.reset(null);
	}
	
	@Test
	public void testtokenIsValidNull() {
		assertFalse(passwordService.tokenIsValid(null).isPresent());
	}
	
	@Test(expected=NullPointerException.class)
	public void testupdatePasswordNull1() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		passwordService.updatePassword(null,"password");
	}
	
	@Test(expected=NullPointerException.class)
	public void testupdatePasswordNull2() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		passwordService.updatePassword(new Utilisateur(),null);
	}
	
	

	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */

	@Test
	public void testresetKO() {
		String mailInexistant = "toto@ymail.com";
		assertFalse(passwordService.reset(mailInexistant));
	}
	
	@Test
	public void testtokenIsValidKO() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Utilisateur user = userService.findByLogin("aa");
		Long duree = new Date().getTime() - 1_600_000;
		String token = Encode.sha256(user.getLogin() + user.getMail() + new Date(duree).toString());
		user.setDatetoken(new Date(duree));
		userService.updateUserInfos(user);
		assertFalse(passwordService.tokenIsValid(token).isPresent());
	}

	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */
	@Test
	public void testresetOK() {
		String mailexistant = "aa@gmail.com";
		assertFalse(passwordService.reset(mailexistant));
	}
	
	@Test
	public void testtokenIsValidOK() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Utilisateur user = userService.findByLogin("aa");
		Long duree = new Date().getTime() ;
		String token = Encode.sha256(user.getLogin() + user.getMail() + new Date(duree).toString());
		user.setToken(token);
		user.setDatetoken(new Date(duree));
		userService.updateUserInfos(user);
		assertTrue(passwordService.tokenIsValid(token).isPresent());
	}
	
	@Test
	public void testupdatePasswordOK() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Utilisateur user = userService.findByLogin("aa");
		String newPasswordEncode = Encode.sha1("aa_newpassword");
		passwordService.updatePassword(user, "aa_newpassword");
		assertTrue(newPasswordEncode.equals(userService.findByLogin("aa").getMotdepasse()));
	}

}
