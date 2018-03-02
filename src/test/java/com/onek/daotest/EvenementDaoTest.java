package com.onek.daotest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.EvenementDao;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EvenementDaoTest {
	
	@Autowired
	private EvenementDao evenementDao;

	/*
	 * TESTS DES ARGUMENTS
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testFindById() {
		evenementDao.findById(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindByIdUser() {
		evenementDao.findByIdUser(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSupprimerEvent() {
		evenementDao.supprimerEvent(-1);
	}

	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionAddEvenement() {
		evenementDao.addEvenement(null);
	}

	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionEditEvenement() {
		evenementDao.editEvenement(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionFindByCode() {
		evenementDao.findByCode(null);
	}
	
}
