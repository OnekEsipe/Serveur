package com.onek.dao;

import com.onek.model.Signature;

/**
 * Interface de la classe SignatureDaoImpl. Requêtes vers la base de données pour les signatures
 */
public interface SignatureDao {
	
	/**
	 * Ajoute une signature dans la base de donnée.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @param signature La signature à ajouter
	 */
	public void addSignature(Signature signature);
}
