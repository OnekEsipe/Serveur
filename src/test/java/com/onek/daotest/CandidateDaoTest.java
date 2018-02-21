package com.onek.daotest;

import java.util.ArrayList;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.CandidatesDao;
import com.onek.model.Candidat;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CandidateDaoTest {


	@Autowired
	private CandidatesDao candidateDao;

	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=IllegalArgumentException.class)
	public void testIllegalIdevent() {
		candidateDao.findCandidatesByEvent(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalIdcandidat() {
		candidateDao.supprimerCandidat(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionaddCandidate() {
		candidateDao.addCandidate(null);;
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionAddCandidates() {
		candidateDao.addCandidates(null);;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNotEmptyList() {
		List<Candidat> emptyList = new ArrayList<>();
		candidateDao.addCandidates(emptyList);
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
