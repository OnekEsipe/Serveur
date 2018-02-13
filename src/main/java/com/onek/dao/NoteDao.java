package com.onek.dao;

import com.onek.model.Note;

public interface NoteDao {

	Note addNote(Note note);
	void update(Note note);

}
