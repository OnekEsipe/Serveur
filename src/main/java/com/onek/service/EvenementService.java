package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;

public interface EvenementService {
	public void addEvenement(Evenement event);
	public Evenement findById(int id);
	public void editEvenement(Evenement event);
	public List<Evenement> findAll();
	public void supprimerEvent(int idevent);
	public List<Evenement> myListEvents(int iduser);
	public Evenement addDuplicatedEvent(Evenement event);
}
