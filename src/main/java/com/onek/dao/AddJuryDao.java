package com.onek.dao;

import java.util.List;

import com.onek.model.Jury;
import com.onek.model.Utilisateur;

public interface AddJuryDao {
	public List<Utilisateur> listJurysByEvent(int idevent);
	public List<Utilisateur> listJurysAll();
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent);
	public void supprimerUtilisateur(int iduser);
	public void addJuryToEvent(Jury jury);
}
