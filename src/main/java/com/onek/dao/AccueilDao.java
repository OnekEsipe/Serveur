package com.onek.dao;

import java.util.List;

import com.onek.model.Evenement;

public interface AccueilDao {
	public List<Evenement> listEvents() ;
	public List<Evenement> myListEvents(int iduser);
	public void supprimerEvent(int idevent) ;
}
