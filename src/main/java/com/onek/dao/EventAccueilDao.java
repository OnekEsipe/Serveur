package com.onek.dao;

import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Utilisateur;

public interface EventAccueilDao {
	public List<Candidat> listCandidatsByEvent(int idevent);
	public List<Utilisateur> listJurysByEvent();
	public List<Utilisateur> listJurysAnnonymesByEvent();
}
