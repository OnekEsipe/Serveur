package com.onek.daotest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.CritereDao;
import com.onek.model.Critere;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CritereDaoTest {

	@Autowired
	private CritereDao critereDao;
	
	/*
	 * TESTS DES ARGUMENTS
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindById() {
		critereDao.findById(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSupprimerCritere() {
		critereDao.supprimerCritere(-1);
	}

	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionAddCritere() {
		critereDao.addCritere(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionAddCriteres() {
		critereDao.addCriteres(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddCriteres() {
		List<Critere> emptyList = new ArrayList<>();
		critereDao.addCriteres(emptyList);
	}

}
