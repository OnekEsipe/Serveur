package com.onek.daotest;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.EvaluationDao;
import com.onek.model.Candidat;
import com.onek.model.Jury;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EvaluationDaoTest {

	@Autowired
	private EvaluationDao evaluationDao;
	
	/*
	 * TESTS DES ARGUMENTS
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId1() {
		evaluationDao.findById(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId2() {
		evaluationDao.deleteEvaluation(-1, 10);
		evaluationDao.deleteEvaluation(10, -1);
		evaluationDao.deleteEvaluation(-1, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId3() {
		evaluationDao.findByIdCandidate(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId4() {
		evaluationDao.findByIdJury(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionUpdate() {
		evaluationDao.update(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionSaveEvaluation1() {
		evaluationDao.saveEvaluation(null, new Jury(), new Date(), 10);
		evaluationDao.saveEvaluation(new Candidat(), null, new Date(), 10);
		evaluationDao.saveEvaluation(new Candidat(), new Jury(), null, 10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalIdSaveEvaluation() {
		evaluationDao.saveEvaluation(new Candidat(),new Jury(),new Date(), -1);
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionSaveEvaluation2() {
		evaluationDao.saveEvaluation(null, "prenom", 10, new Jury(), new Date());
		evaluationDao.saveEvaluation("nom", null, 10, new Jury(), new Date());
		evaluationDao.saveEvaluation("nom", "prenom", 10,null, new Date());
		evaluationDao.saveEvaluation("nom", "prenom", 10,new Jury(), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalIdSaveEvaluation2() {
		evaluationDao.saveEvaluation("nom", "prenom", -1,new Jury(), new Date());
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
