package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;

/**
 * Interface de la classe EvenementServiceImpl. Couche service
 */
public interface EvenementService {
	
	/**
	 * @see com.onek.dao.EvenementDao#addEvenement(Evenement)
	 */
	public void addEvenement(Evenement event);
	
	/**
	 * @see com.onek.dao.EvenementDao#findById(int)
	 */
	public Evenement findById(int id);
	
  /**
	 * @see com.onek.dao.EvenementDao#findByCode(String)
	 */
	public Evenement findByCode(String code);

  /**
	 * @see com.onek.dao.EvenementDao#editEvenement(Evenement)
	 */
	public void editEvenement(Evenement event);
	
	/**
	 * @see com.onek.dao.EvenementDao#findAll()
	 */
	public List<Evenement> findAll();
	
	/**
	 * @see com.onek.dao.EvenementDao#supprimerEvent(int)
	 */
	public void supprimerEvent(int idevent);
	
	/**
	 * @see com.onek.dao.EvenementDao#findByIdUser(int)
	 */
	public List<Evenement> myListEvents(int iduser);

	/**
	 * @see com.onek.dao.EvenementDao#addDuplicatedEvent(Evenement)
	 */
	public Evenement addDuplicatedEvent(Evenement event);
}
