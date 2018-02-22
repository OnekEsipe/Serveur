package com.onek.utilstest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import com.onek.utils.StatutEvenement;

public class StatutEvenementTest {

	@Test
	public void testCorrespondingValuesAndString() {
		assertEquals("Brouillon", StatutEvenement.BROUILLON.toString());
		assertEquals("Ouvert", StatutEvenement.OUVERT.toString());
		assertEquals("Ferme", StatutEvenement.FERME.toString());
	}
	
	@Test
	public void testNotNullValue() {
		List<StatutEvenement>  statuts = Arrays.asList(StatutEvenement.values());
		statuts.forEach(statut -> {
			assertNotNull(statut);
		});
	}
	
	@Test
	public void testNoEmptyToString() {
		List<StatutEvenement>  statuts = Arrays.asList(StatutEvenement.values());
		statuts.forEach(droit -> {
			assertNotEquals("", droit.toString());
		});
	}

}
