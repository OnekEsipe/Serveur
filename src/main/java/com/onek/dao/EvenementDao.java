package com.onek.dao;

import com.onek.model.Evenement;

public interface EvenementDao {

	public void addEvenement(Evenement event);
	public Evenement findByName(String name);
}
