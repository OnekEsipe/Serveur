package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvenementDao;
import com.onek.model.Evenement;

@Service
public class EventAccueilServiceImpl implements EventAccueilService, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired 
	private EvenementDao eventDao;

	@Override
	public void editEvenement(Evenement event) {
		eventDao.editEvenement(event);
	}
}
