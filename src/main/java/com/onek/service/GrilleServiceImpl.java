package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.CritereDao;
import com.onek.model.Critere;

@Service
public class GrilleServiceImpl implements GrilleService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CritereDao critereDao;
	
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
	
}
