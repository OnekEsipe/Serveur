package com.onek.daotest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.CritereDao;
import com.onek.dao.EvaluationDao;
import com.onek.dao.NoteDao;
import com.onek.model.Note;


@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NoteDaoTest {
	
	@Autowired
	private NoteDao noteDao;
	
	@Autowired
	private CritereDao critereDao;
	
	@Autowired
	private EvaluationDao evaluationDao;
	
	/*
	 * TESTS DES ARGUMENTS
	 */

	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionAddNote() {
		noteDao.addNote(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionUpdate() {
		noteDao.update(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindNoteById() {
		noteDao.findNoteById(-1);
	}
	
	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * 
	 */
	@Test
	public void testGetAllNotesOK() {
		assertTrue(!noteDao.getAllNotes().isEmpty());
	}
	
	@Test
	public void testfindNoteByIdOK() {
		assertNotNull(noteDao.findNoteById(3));
	}
	
	@Test
	public void testAddNoteOK() {
		Note note = new Note();
		note.setCommentaire("test note");
		note.setDate(new Date());
		note.setCritere(critereDao.findById(1));
		note.setEvaluation(evaluationDao.findById(2));
		int nbNotesExpected = noteDao.getAllNotes().size() + 1;
		noteDao.addNote(note);
		assertTrue(nbNotesExpected == noteDao.getAllNotes().size());
	}
	
	@Test
	public void testUpdateOK() {
		Note note = noteDao.findNoteById(3);
		note.setCommentaire("commentaire de test");
		noteDao.update(note);
		assertTrue(noteDao.findNoteById(3).getCommentaire().equals("commentaire de test"));
	}
	
	
	

}
