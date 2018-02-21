package com.onek.service;

import java.util.HashMap;
import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;

public interface JuryService {
	List<Jury> findJurysByIdevent(int idevent);
	HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent);
	public List<Utilisateur> listJurysByEvent(int idevent);
	public List<Jury> listJurysAnnonymesByEvent(int idevent) ;
	public List<Utilisateur> findAllJurys();
	public void supprimerUtilisateur(int iduser,int idevent);
	public void addJuryToEvent(Jury jury);
	public List<Jury> findAnonymousByIdEvent(int idEvent);
	public void addListJurys(List<Jury> jurys);
	public Utilisateur findById(int id) ;
}
