package com.onek.dao;

import java.util.List;

import com.onek.model.Note;

/**
 * Interface de la classe NoteDaoImpl. Requêtes vers la base de données pour les notes
 */
public interface NoteDao {

	/**
	 * Ajoute une note dans la base de donnée.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @param note Une note
	 * @return La note passée en paramètre
	 */
	Note addNote(Note note);
	
	/**
	 * Mets à jour la note dans la base de donnée.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @param note La note à mettre à jour
	 */
	void update(Note note);
	public List<Note> getAllNotes();
	public Note findNoteById(int idnote);

}
