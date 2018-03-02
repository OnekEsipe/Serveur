package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.CritereDao;
import com.onek.dao.DescripteurDao;
import com.onek.model.Critere;
import com.onek.model.Descripteur;

/**
 * Service GrilleServiceImpl
 */
@Service
public class GrilleServiceImpl implements GrilleService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CritereDao critereDao;
	
	@Autowired
	private DescripteurDao descripteurDao;
	
	@Override
	public void addCriteres(List<Critere> criteres) {
		critereDao.addCriteres(criteres);		
	}
	
	@Override
	public void addCritere(Critere critere) {
		critereDao.addCritere(critere);
	}

	@Override
	public Critere getCritereById(int id) {
		return critereDao.findById(id);
	}

	@Override
	public void supprimerCritere(int id) {
		critereDao.supprimerCritere(id);
	}

	@Override
	public void updateCritere(Critere critere) {
		critereDao.updateCritere(critere);
	}

	@Override
	public void supprimerDescripteur(Descripteur descripteur) {
		descripteurDao.supprimerDescripteur(descripteur);
	}
	
}
