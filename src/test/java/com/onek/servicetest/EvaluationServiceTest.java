package com.onek.servicetest;

import static org.junit.Assert.*;




import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.model.Candidat;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.service.CandidateService;
import com.onek.service.EvaluationService;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EvaluationServiceTest {

	@Autowired
	private EvaluationService evaluationService;

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

	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 */
	@Test
	public void testfindByIdCandidateKO() {
		assertTrue(evaluationService.findByIdCandidate(0).isEmpty());
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
		evaluationService.deleteEvaluation(0,1);
	}


}
