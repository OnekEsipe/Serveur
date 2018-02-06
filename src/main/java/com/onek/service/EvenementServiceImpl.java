package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.onek.dao.EvenementDao;
import com.onek.model.Evenement;

public class EvenementServiceImpl implements EvenementService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EvenementDao eventDao;
	
	@Override
	public void addEvenement(Evenement event) {
		eventDao.addEvenement(event);		
	}
}
