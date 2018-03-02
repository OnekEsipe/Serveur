package com.onek.servicetest;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.model.Candidat;
import com.onek.model.Evaluation;
import com.onek.model.Jury;
import com.onek.service.CandidateService;
import com.onek.service.EvaluationService;
import com.onek.service.JuryService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EvaluationServiceTest {

	@Autowired
	private EvaluationService evaluationService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private JuryService juryService;

	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=IllegalArgumentException.class)
	public void testfindByIdCandidate() {
		evaluationService.findByIdCandidate(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testdeleteEvaluation1() {
		evaluationService.deleteEvaluation(-1,1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testdeleteEvaluation2() {
		evaluationService.deleteEvaluation(1,-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testupdateNull() {
		evaluationService.update(null);;
	}

	@Test(expected=NullPointerException.class)
	public void testsaveEvaluation1() {
		evaluationService.saveEvaluation(null, new Jury(), new Date(), 1);
	}

	@Test(expected=NullPointerException.class)
	public void testsaveEvaluation2() {
		evaluationService.saveEvaluation(new Candidat(), null, new Date(), 1);
	}

	@Test(expected=NullPointerException.class)
	public void testsaveEvaluation3() {
		evaluationService.saveEvaluation(new Candidat(), new Jury(),null, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testsaveEvaluation4() {
		evaluationService.saveEvaluation(new Candidat(), new Jury(),new Date(), -1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testfindByIdJury() {
		evaluationService.findByIdJury(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindById() {
		evaluationService.findById(-1);
	}

	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */
	@Test
	public void testfindByIdCandidateKO() {
		assertTrue(evaluationService.findByIdCandidate(Integer.MAX_VALUE).isEmpty());
	}
	
	@Test
	public void testfindByIdJuryKO() {
		assertTrue(evaluationService.findByIdJury(Integer.MAX_VALUE).isEmpty());
	}
	
	@Test
	public void testfindByIdKO() {
		assertNull(evaluationService.findById(Integer.MAX_VALUE));
	}

	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */
	@Test
	public void testfindByIdCandidateOK() {
		assertTrue(evaluationService.findByIdCandidate(1).size() > 0); // Necessite au moins une evaluation associée au candidat de id=1
	}

	@Test
	public void testdeleteEvaluationOK() {
		assertTrue(evaluationService.findByIdCandidate(2).size() > 0);
		evaluationService.deleteEvaluation(3,2);
		assertTrue(evaluationService.findByIdCandidate(2).isEmpty());
	}

	@Test
	public void testfindByIdJuryOK() {
		assertTrue(evaluationService.findByIdJury(2).size() > 0); // Necessite au moins une evaluation associée au jury de id=2
	}
	
	@Test
	public void testfindByIdOK() {
		assertNotNull(evaluationService.findById(2)); 
	}

	@Test
	public void testupdateOK() {
		String modif = "Commentaire modifié";
		Evaluation evaluation = evaluationService.findById(2);
		assertFalse(evaluation.getCommentaire().equals(modif));
		evaluation.setCommentaire(modif);
		evaluationService.update(evaluation);
		assertTrue(evaluationService.findById(2).getCommentaire().equals(modif));
	}
	
	@Test
	public void testsaveEvaluationOK() {
		Candidat candidat = candidateService.findCandidatesById(1);
		Jury jury = juryService.findJuryById(2);
		int nbEvalExpected = evaluationService.findByIdJury(2).size() + 1;
		Date date = new Date();
		int idevent = 1;
		assertNotNull(candidat);
		assertNotNull(jury);
		evaluationService.saveEvaluation(candidat, jury, date, idevent);
		assertTrue(nbEvalExpected == evaluationService.findByIdJury(2).size());
		
	}


}
