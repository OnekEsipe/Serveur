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
	
	
	/** Récupère toutes les notes
	 * @return Liste des notes
	 */
	public List<Note> getAllNotes();
	
	/**
	 * Récupère une note par son id
	 * @param idnote Id de la note
	 * @return La note 
	 */
	public Note findNoteById(int idnote);
}
