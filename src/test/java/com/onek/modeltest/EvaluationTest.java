package com.onek.modeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.model.Evaluation;

public class EvaluationTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getIdTest() {
		Evaluation evaluation = Mockito.mock(Evaluation.class);

		Mockito.when(evaluation.getIdevaluation()).thenReturn(1);

		assertEquals(1, evaluation.getIdevaluation().intValue());
	}
	
	@Test
	public void commentaireTest() {
		Evaluation evaluation = Mockito.mock(Evaluation.class);
		Mockito.when(evaluation.getCommentaire()).thenCallRealMethod();

		assertEquals(null, evaluation.getCommentaire());

		Mockito.doCallRealMethod().when(evaluation).setCommentaire(Mockito.anyString());

		evaluation.setCommentaire("Ceci est un commentaire de test!");
		assertEquals("Ceci est un commentaire de test!", evaluation.getCommentaire());
	}

}
