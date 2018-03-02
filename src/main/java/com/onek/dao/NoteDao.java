package com.onek.dao;

import java.util.List;

import com.onek.model.Note;

public interface NoteDao {

	Note addNote(Note note);
	void update(Note note);
	public List<Note> getAllNotes();
	public Note findNoteById(int idnote);

}
