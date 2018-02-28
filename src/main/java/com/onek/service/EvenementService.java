package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;

public interface EvenementService {
	public Evenement addEvenement(Evenement event);
	public Evenement findById(int id);
	public Evenement findByCode(String code);
	public void editEvenement(Evenement event);
	public List<Evenement> findAll();
	public void supprimerEvent(int idevent);
	public List<Evenement> myListEvents(int iduser);
}
