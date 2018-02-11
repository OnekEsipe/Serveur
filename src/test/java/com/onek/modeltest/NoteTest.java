package com.onek.modeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.model.Note;

public class NoteTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getIdTest() {
		Note note = Mockito.mock(Note.class);

		Mockito.when(note.getIdnote()).thenReturn(1);

		assertEquals(1, note.getIdnote().intValue());
	}

	@Test
	public void commentaireTest() {
		Note note = Mockito.mock(Note.class);
		Mockito.when(note.getCommentaire()).thenCallRealMethod();

		assertEquals(null, note.getCommentaire());

		Mockito.doCallRealMethod().when(note).setCommentaire(Mockito.anyString());

		note.setCommentaire("Ceci est un commentaire de test!");
		assertEquals("Ceci est un commentaire de test!", note.getCommentaire());
	}
	
	@Test
	public void niveauTest() {
		Note note = Mockito.mock(Note.class);

		Mockito.when(note.getNiveau()).thenReturn(1);

		assertEquals(1, note.getNiveau().intValue());
	}

}
