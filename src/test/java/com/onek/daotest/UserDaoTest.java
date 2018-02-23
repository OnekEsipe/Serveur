package com.onek.daotest;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.UserDao;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;

	/*
	 * TESTS DES ARGUMENTS
	 */
	
	@Test(expected=NullPointerException.class)
	public void testUpdateUserInfos() {
		userDao.updateUserInfos(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFindByLogin() {
		userDao.findByLogin(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testAddJurysAnonymes1() {
		userDao.addJurysAnonymes(null, new Evenement());
	}
	
	@Test(expected=NullPointerException.class)
	public void testAddJurysAnonymes2() {
		userDao.addJurysAnonymes(new ArrayList<>(), null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddJurysAnonymes3() {
		List<Utilisateur> emptyList = new ArrayList<>();
		userDao.addJurysAnonymes(emptyList, new Evenement());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteUser() {
		userDao.deleteUser(-1);
	}

	@Test(expected=NullPointerException.class)
	public void testAddUser() {
		userDao.addUser(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAllUsersExceptCurrent() {
		userDao.getAllUsersExceptCurrent(-1);
	}

	@Test(expected=NullPointerException.class)
	public void testMailExist() {
		userDao.mailExist(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testUserExist() {
		userDao.userExist(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFindByMail() {
		userDao.findByMail(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindUserById() {
		userDao.findUserById(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testEditUser() {
		userDao.editUser(null);
	}
	
	


	
	
	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 * TODO
	 */	
	
	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * TODO
	 */

}
