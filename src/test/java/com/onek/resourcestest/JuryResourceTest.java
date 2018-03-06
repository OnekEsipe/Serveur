package com.onek.resourcestest;

import java.util.ArrayList;

import org.junit.Test;

import com.onek.model.Jury;
import com.onek.model.Utilisateur;
import com.onek.resource.JuryResource;

public class JuryResourceTest {
	
	@Test
	public void testConstructor() {		
		new JuryResource(createJury());
	}
	
	@Test
	public void testSetterCandidate() {
		JuryResource jr = new JuryResource(createJury());
		jr.setCandidates(new ArrayList<>());
	}
	
	private Jury createJury() {
		Utilisateur user = new Utilisateur();
		user.setNom("nom");
		user.setPrenom("prenom");
		Jury j = new Jury();
		j.setUtilisateur(user);
		return j;
	}
}
