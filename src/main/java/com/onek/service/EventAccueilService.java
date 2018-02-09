package com.onek.service;

import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Utilisateur;

public interface EventAccueilService {
	public List<Candidat> listCandidatsByEvent(int idevent);
	public List<Utilisateur> listJurysByEvent(int idevent);
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent) ;
	public void supprimerCandidat(int idcandidat);
}
