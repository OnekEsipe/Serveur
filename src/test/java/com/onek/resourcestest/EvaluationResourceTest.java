package com.onek.resourcestest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evaluation;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Note;
import com.onek.model.Utilisateur;
import com.onek.resource.EvaluationResource;

public class EvaluationResourceTest {

	@Test
	public void testEmptyConstructor() {
		new EvaluationResource();
	}
	
	@Test
	public void testConstructor() {	
		Descripteur d = new Descripteur();
		d.setNiveau("A");
		d.setTexte("texte");
		ArrayList<Descripteur> ds = new ArrayList<>();
		ds.add(d);		
		Critere critere = new Critere();
		critere.setIdcritere(1);
		critere.setCategorie("categorie");
		critere.setTexte("texte");
		critere.setDescripteurs(ds);	
		ArrayList<Note> notes = new ArrayList<>();
		Note note = new Note();
		note.setCommentaire("commentaire note");
		note.setIdnote(1);
		note.setNiveau(-1);
		note.setCritere(critere);
		Candidat c = new Candidat();
		c.setIdcandidat(5);
		Evenement event = new Evenement();
		event.setIdevent(2);
		Jury j = new Jury();
		Utilisateur u = new Utilisateur();
		u.setIduser(3);
		j.setUtilisateur(u);
		j.setEvenement(event);
		Date date = new Date();
		Evaluation e = new Evaluation();
		e.setIdevaluation(1);
		e.setJury(j);	
		e.setCandidat(c);
		e.setCommentaire("commentaire");
		e.setDatedernieremodif(date);
		e.setIssigned(true);
		e.setNotes(notes); 
		new EvaluationResource(e); 
	}
	
	@Test
	public void validGetter() {
		Descripteur d = new Descripteur();
		d.setNiveau("A");
		d.setTexte("texte");
		ArrayList<Descripteur> ds = new ArrayList<>();
		ds.add(d);		
		Critere critere = new Critere();
		critere.setIdcritere(1);
		critere.setCategorie("categorie");
		critere.setTexte("texte");
		critere.setDescripteurs(ds);	
		ArrayList<Note> notes = new ArrayList<>();
		Note note = new Note();
		note.setCommentaire("commentaire note");
		note.setIdnote(1);
		note.setNiveau(-1);
		note.setCritere(critere);
		note.setDate(new Date());
		Candidat c = new Candidat();
		c.setIdcandidat(5);
		Evenement event = new Evenement();
		event.setIdevent(2);
		Jury j = new Jury();
		Utilisateur u = new Utilisateur();
		u.setIduser(3);
		j.setUtilisateur(u);
		j.setEvenement(event);
		Date date = new Date();
		Evaluation e = new Evaluation();
		e.setIdevaluation(1);
		e.setJury(j);	
		e.setCandidat(c);
		e.setCommentaire("commentaire");
		e.setDatedernieremodif(date);
		e.setIssigned(true);
		e.setNotes(notes);
		EvaluationResource er = new EvaluationResource(e); 
		assertEquals(new Integer(1), er.getIdEvaluation());
		assertEquals("commentaire", er.getComment());	
		assertTrue(er.getIsSigned());
		assertEquals(date, er.getDateLastChange());
		assertEquals(new Integer(2), er.getIdEvent());
		assertNull(er.getSignatures());
		assertNotNull(er.getNotes());
	}
	
	@Test
	public void testDate() {
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");		
		EvaluationResource er = new EvaluationResource();
		er.setDateLastChange(formater.format(date));
		assertEquals(formater.format(date), er.getDateLastChangeString());
	}
	
	@Test
	public void testSetDateFailure() {	
		EvaluationResource er = new EvaluationResource();
		er.setDateLastChange("toto"); 
	}
	
	@Test
	public void testGetDateFailure() {
		EvaluationResource er = new EvaluationResource();
		er.getDateLastChangeString();
	}
}
