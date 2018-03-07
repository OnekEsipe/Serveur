package com.onek.resourcestest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evenement;
import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;
import com.onek.resource.JuryResource;

public class EvenementResourceTest {

	@Test
	public void testConstructor() {	
		new EvenementResource(createEvenement());
	}
	
	@Test
	public void testSetter() {
		EvenementResource er = new EvenementResource(createEvenement());
		er.setJurys(new ArrayList<JuryResource>());
		er.setEvaluations(new ArrayList<EvaluationResource>());
	}
	
	@Test
	public void testDate() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = new Date();
		Evenement e = createEvenement();
		e.setDatestart(date);
		e.setDatestop(date);
		EvenementResource er = new EvenementResource(e);
		assertEquals(formater.format(date), er.getDateStart());
		assertEquals(formater.format(date), er.getDateStop());
	}
	
	private Descripteur createDescripteur() {
		Descripteur d = new Descripteur();
		d.setIddescripteur(1);
		d.setNiveau("A");
		d.setPoids(BigDecimal.valueOf(1.0));
		d.setTexte("texte");
		d.setCritere(createCritere());
		return d;
	}
	
	private Critere createCritere() {
		Critere c = new Critere();
		c.setCategorie("categorie");
		c.setCoefficient(BigDecimal.valueOf(2.0));
		c.setIdcritere(1);
		c.setTexte("texte");
		c.setDescripteurs(new ArrayList<>());
		return c;
	}
	
	private Evenement createEvenement() {
		ArrayList<Critere> criteres = new ArrayList<>();		
		for(int i = 0 ; i < 2 ; i++) {	
			Critere critere = createCritere();
			critere.addDescripteur(createDescripteur());	
			criteres.add(critere);
		}		
		Evenement e = new Evenement();
		e.setCriteres(criteres);
		return e;	
	}
}
