package com.onek.dao;

import java.util.List;

import com.onek.model.Candidat;

/**
 * Interface de la classe CandidatesDaoImpl. Requêtes vers la base de données pour les candidats
 */
public interface CandidatesDao {
	
	/**
	 * Recupère la liste des candidats pour un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si l'id de l'événement est inférieur à 1
	 * @param idEvent Id de l'événement
	 * @return List de candidats
	 */
	public List<Candidat> findCandidatesByEvent(int idEvent);
	
	/**
	 * Enregistre un candidat dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param candidat Un candidat
	 */
	public void addCandidate(Candidat candidat);
	
	/**
	 * Enregistre une liste de candidats dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si la liste est vide
	 * @param candidates Une liste de candidat
	 */
	public void addCandidates(List<Candidat> candidates);
	
	/**
	 * Supprime un candidat, ses évaluations et les notes associées dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id candidat est inférieur à 1
	 * @param idcandidat Id du candidat
	 */
	public void supprimerCandidat(int idcandidat);
	
	/**
	 * Récupére un candidat par son identifiant.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id candidat est inférieur à 1
	 * @param idcandidat Id du candidat
	 * @return Candidat
	 */
	public Candidat findCandidatesById(int idcandidat);
}
