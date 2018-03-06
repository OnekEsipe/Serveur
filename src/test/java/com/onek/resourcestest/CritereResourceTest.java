package com.onek.resourcestest;

import java.util.ArrayList;

import org.junit.Test;

import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.resource.CritereResource;

public class CritereResourceTest {
	
	@Test
	public void testConstructor() {
		ArrayList<Descripteur> ds = new ArrayList<>();
		ds.add(new Descripteur());		
		Critere c = new Critere();
		c.setIdcritere(1);
		c.setCategorie("categorie");
		c.setTexte("texte");
		c.setDescripteurs(ds);
		new CritereResource(c);
	}
	
}
