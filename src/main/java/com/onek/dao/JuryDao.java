package com.onek.dao;

import java.util.HashMap;
import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;

public interface JuryDao {
	public boolean juryIsAssigned(int idUser, int idEvent);
	public List<Jury> findJuryAndAnonymousByIdEvent(int idEvent, String login);
	public List<Jury> findAnonymousByIdEvent(int idEvent);
	public List<Jury> findByUser(Utilisateur user);
	public List<Jury> findJurysByIdevent(int idevent);
	public HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent);	
	public List<Utilisateur> listJurysByEvent(int idevent);
	public List<Utilisateur> findJurysAnnonymesByEvent(int idevent);
	public void supprimerUtilisateur(int iduser, int idevent);
	public void addJuryToEvent(Jury jury);
	public void addListJurys(List<Jury> jurys);
	public Utilisateur findById(int id) ;
	public Jury findJuryById(int id);
	public void supprimerUtilisateurAnonyme(int iduser);

}
