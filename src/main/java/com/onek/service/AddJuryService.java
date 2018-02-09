package com.onek.service;

import java.util.List;

import com.onek.model.Utilisateur;

public interface AddJuryService {
	public List<Utilisateur> listJurysByEvent(int idevent);
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent) ;
	public List<Utilisateur> listJurysAll();
}
