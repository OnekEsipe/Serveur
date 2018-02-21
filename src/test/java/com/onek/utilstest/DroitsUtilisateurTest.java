package com.onek.utilstest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.onek.utils.DroitsUtilisateur;

public class DroitsUtilisateurTest {

	@Test
	public void testCorrespondingValuesAndString() {
		assertEquals("R", DroitsUtilisateur.ADMINISTRATEUR.toString());
		assertEquals("O", DroitsUtilisateur.ORGANISATEUR.toString());
		assertEquals("J", DroitsUtilisateur.JURY.toString());
		assertEquals("A", DroitsUtilisateur.ANONYME.toString());
	}
	
	@Test
	public void testNotNullValue() {
		List<DroitsUtilisateur>  droits = Arrays.asList(DroitsUtilisateur.values());
		droits.forEach(droit -> {
			assertNotNull(droit);
		});
	}
	
	@Test
	public void testNoEmptyToString() {
		List<DroitsUtilisateur>  droits = Arrays.asList(DroitsUtilisateur.values());
		droits.forEach(droit -> {
			assertNotEquals("", droit.toString());
		});
	}
	
	@Test
	public void testSizeOfEnumValue() {
		List<DroitsUtilisateur>  droits = Arrays.asList(DroitsUtilisateur.values());
		droits.forEach(droit -> {
			assertEquals(1, droit.toString().length());
		});
		
	}

}
