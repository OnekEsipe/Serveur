package com.onek.dao;

import java.util.List;

import com.onek.model.Evenement;

public interface EvenementDao {

	public void addEvenement(Evenement event);
	public Evenement findById(int id);
	public List<Evenement> findByIdUser(int idUser);
	public Evenement findByCode(String code);
	public void editEvenement(Evenement event);
}
