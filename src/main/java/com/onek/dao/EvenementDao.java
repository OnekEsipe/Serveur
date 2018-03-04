package com.onek.dao;

import java.util.List;

import com.onek.model.Evenement;

/**
 * Interface de la classe EvenementDaoImpl. Requêtes vers la base de données pour les evenements
 */
public interface EvenementDao {

	/**
	 * Enregistre un evenement dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param Evenement Un evenement
	 * @return Evenement ajouté
	 */
	public Evenement addEvenement(Evenement event);
	
	/**
	 * Récupére un evenement par son identifiant.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id evenement est inférieur à 1
	 * @param id Id de l'événement
	 * @return Evenement
	 */
	public Evenement findById(int id);
	
	/**
	 * Recupère la liste des candidats pour un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si l'id de l'événement est inférieur à 1
	 * @param idEvent Id de l'événement
	 * @return List de candidats
	 */
	public List<Evenement> findByIdUser(int idUser);
	
	/**
	 * Recupère l'événement par son code événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @param code Code de l'événement
	 */
	public Evenement findByCode(String code);
	
	/**
	 * Met à jour un événement dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @param event L'évenement modifié
	 */
	public void editEvenement(Evenement event);
	
	/**
	 * Recupère les événements depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @return Evenements La liste des événements
	 */
	public List<Evenement> findAll();
	
	/**
	 * Met le champ isDeleted de l'événement à true dans le base de données
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id événement est inférieur à 1
	 * @param idevent Id de l'événement
	 */
	public void supprimerEvent(int idevent);
}