package com.onek.resourcestest;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Note;
import com.onek.resource.DescripteurResource;
import com.onek.resource.NoteResource;

public class NoteResourceTest {

	@Test
	public void testEmptyConstructor() {
		new NoteResource();
	}
	
	@Test
	public void testConstructor() {
		new NoteResource(createNote(-1));
		new NoteResource(createNote(0));
	}
	
	@Test
	public void testGetter() {
		NoteResource nr = new NoteResource(createNote(0));
		assertEquals(new Integer(1), nr.getIdCriteria());
	}
	
	@Test
	public void testDate() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		NoteResource nr = new NoteResource(createNote(0));
		Date date = new Date();
		nr.setDate(formater.format(date));
		assertEquals(formater.format(date), nr.getDateString());
	}
	
	@Test
	public void testDateNull() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		NoteResource nr = new NoteResource(createNote(0));
		nr.setDate("toto");
		assertEquals(formater.format(new Date()), nr.getDateString());
	}
	
	@Test
	public void testCreateNote() {
		NoteResource nr = new NoteResource(createNote(0));
		Note note = nr.createNote();
		assertEquals(new Integer(0), note.getNiveau());
		assertEquals("commentaire", note.getCommentaire());
	}
	
	@Test
	public void testSetterSelectedLevelEmpty() {
		NoteResource nr = new NoteResource(createNote(0));
		nr.setSelectedLevel("");
	}
	
	@Test
	public void testSetterSelectedLevel() {
		NoteResource nr = new NoteResource(createNote(0));
		nr.setSelectedLevel("A");
	}
	
	@Test
	public void testGetterSelectedLevelEmpty() {
		NoteResource nr = new NoteResource(createNote(-1));
		assertEquals("", nr.getSelectedLevel());
	}
	
	@Test
	public void testGetterSelectedLevel() {
		NoteResource nr = new NoteResource(createNote(0));
		assertEquals("A", nr.getSelectedLevel());
	}
	
	@Test
	public void testGetterSelectedDescriptor() {
		NoteResource nr = new NoteResource(createNote(0));
		DescripteurResource dr = nr.getSelectedDescriptor();
		assertEquals("A", dr.getNiveau());
	}
	
	public Note createNote(Integer niveau) {
		ArrayList<Descripteur> ds = new ArrayList<>();
		Descripteur d = new Descripteur();
		d.setNiveau("A");
		ds.add(d);		
		Critere c = new Critere();
		c.setIdcritere(1);
		c.setCategorie("categorie");
		c.setTexte("texte");
		c.setDescripteurs(ds);
		Note note = new Note();
		note.setIdnote(-1);
		note.setNiveau(niveau);
		note.setCommentaire("commentaire");
		note.setCritere(c);
		return note;
	}
}
