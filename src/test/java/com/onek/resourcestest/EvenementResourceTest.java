package com.onek.resourcestest;

import static org.junit.Assert.assertEquals;

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
		ArrayList<Critere> criteres = new ArrayList<>();
		Critere critere = new Critere();
		critere.addDescripteur(new Descripteur());
		criteres.add(critere);
		Evenement e = new Evenement();
		e.setCriteres(criteres);
		new EvenementResource(e);
	}
	
	@Test
	public void testSetter() {
		EvenementResource er = new EvenementResource(new Evenement());
		er.setJurys(new ArrayList<JuryResource>());
		er.setEvaluations(new ArrayList<EvaluationResource>());
	}
	
	@Test
	public void testDate() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = new Date();
		Evenement e = new Evenement();
		e.setDatestart(date);
		e.setDatestop(date);
		EvenementResource er = new EvenementResource(e);
		assertEquals(formater.format(date), er.getDateStart());
		assertEquals(formater.format(date), er.getDateStop());
	}
}
