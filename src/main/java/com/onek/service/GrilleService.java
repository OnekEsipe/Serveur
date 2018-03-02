package com.onek.service;

import java.util.List;

import com.onek.model.Critere;
import com.onek.model.Descripteur;

/**
 * Interface de la classe GrilleServiceImpl. Couche service
 */
public interface GrilleService {

	/**
	 * @see com.onek.dao.CritereDao#addCriteres(java.util.List)
	 */
	public void addCriteres(List<Critere> criteres);
	
	/**
	 * @see com.onek.dao.CritereDao#addCritere(Critere)
	 */
	public void addCritere(Critere critere);
	
	/**
	 * @see com.onek.dao.CritereDao#findById(int)
	 */
	public Critere getCritereById(int id);
	
	/**
	 * @see com.onek.dao.CritereDao#supprimerCritere(int)
	 */
	public void supprimerCritere(int id);
	
	/**
	 * @see com.onek.dao.CritereDao#updateCritere(Critere)
	 */
	public void updateCritere(Critere critere);
	
	/**
	 * @see com.onek.dao.DescripteurDao#supprimerDescripteur(Descripteur)
	 */
	public void supprimerDescripteur(Descripteur descripteur);
}
