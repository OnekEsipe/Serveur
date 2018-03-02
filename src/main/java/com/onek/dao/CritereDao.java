package com.onek.dao;

import java.util.List;

import com.onek.model.Critere;

/**
 * Interface de la classe CritereDaoImpl. Requêtes vers la base de données pour les criteres
 */
public interface CritereDao {
	/**
	 * Enregistre une liste de criteres dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si la liste est vide
	 * @param criteres Une liste de criteres
	 */
	void addCriteres(List<Critere> criteres);
	
	/**
	 * Récupére un critere par son identifiant
	 * @exception IllegalArgumentException Si l'id critere est inférieur à 1
	 * @param id Id du critere
	 * @return Critere
	 */
	Critere findById(Integer id);
	
	/**
	 * Enregistre un critere dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param critere Un critere
	 */
	void addCritere(Critere critere);
	
	/**
	 * Supprime un critere et ses descripteurs dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id critere est inférieur à 1
	 * @param id Id du candidat
	 */
	void supprimerCritere(int id);
	
	/**
	 * Met à jour un critere et sauvegarde les descripteurs dans la base de données
	 * @param critere Un critere
	 */
	void updateCritere(Critere critere);
}
