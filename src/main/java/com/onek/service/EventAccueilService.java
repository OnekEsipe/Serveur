package com.onek.service;

import com.onek.model.Evenement;

/**
 * Interface de la classe EventAccueilServiceImpl. Couche service
 */
public interface EventAccueilService {
	
	/**
	 * @see com.onek.dao.EvenementDao#editEvenement(Evenement)
	 */
	public void editEvenement(Evenement event);
}
