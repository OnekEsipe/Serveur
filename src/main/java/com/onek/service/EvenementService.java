package com.onek.service;

import com.onek.model.Evenement;

public interface EvenementService {
	public void addEvenement(Evenement event);
	public boolean isValid(Evenement event);
	public Evenement findById(int id);
	public void editEvenement(Evenement event);
}
