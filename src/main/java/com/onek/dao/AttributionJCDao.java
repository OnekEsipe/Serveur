package com.onek.dao;

import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Utilisateur;

public interface AttributionJCDao {
	public List<Candidat> listCandidatsByEvent(int idevent);
	public List<Utilisateur> listJurysByEvent(int idevent);
}
