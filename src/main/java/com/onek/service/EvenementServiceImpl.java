package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvenementDao;
import com.onek.model.Evenement;

@Service
public class EvenementServiceImpl implements EvenementService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EvenementDao eventDao;
	
	@Override
	public void addEvenement(Evenement event) {
		eventDao.addEvenement(event);		
	}

	@Override
	public boolean isValid(Evenement event) {
		return true; // TODO
	}

	@Override
	public Evenement findById(int id) {
		return eventDao.findById(id);
	}

	@Override
	public void editEvenement(Evenement event) {
		eventDao.editEvenement(event);
	}
	
	@Override
	public List<Evenement> findAll(){
		return eventDao.findAll();
	}
	
	@Override
	public void supprimerEvent(int idevent) {
		eventDao.supprimerEvent(idevent);
	}
	
	@Override
	public List<Evenement> myListEvents(int iduser) {		
		return eventDao.findByIdUser(iduser);
	}
}
