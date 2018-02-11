package com.onek.dao;

import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Utilisateur;

public interface EventAccueilDao {
	public List<Candidat> listCandidatsByEvent(int idevent);
	public List<Utilisateur> listJurysByEvent(int idevent);
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent);
	public void supprimerCandidat(int idcandidat);
	public void supprimerUtilisateur(int iduser);
}
