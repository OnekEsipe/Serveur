package com.onek.dao;

import com.onek.model.Descripteur;

/**
 * Interface de la classe DescripteurDaoImpl. Requêtes vers la base de données pour les descripteurs
 */
public interface DescripteurDao {
	
	/**
	 * Supprime le descripteur dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param descripteur Un descripteur
	 */
	void supprimerDescripteur(Descripteur descripteur);
}
