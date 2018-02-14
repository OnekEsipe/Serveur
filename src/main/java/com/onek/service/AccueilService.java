package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;

public interface AccueilService {
	public List<Evenement> listEvents();
	public List<Evenement> myListEvents(int iduser);
	public void supprimerEvent(int idevent);
}
