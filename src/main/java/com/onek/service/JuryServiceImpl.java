package com.onek.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.JuryDao;
import com.onek.dao.UserDao;
import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;

@Service
public class JuryServiceImpl implements JuryService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private JuryDao juryDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<Jury> findJurysByIdevent(int idevent) {
		return juryDao.findJurysByIdevent(idevent);
	}
	
	@Override
	public HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent) {
		return juryDao.associatedJurysCandidatesByEvent(jurys, idevent);
	}
	
	@Override
	public List<Utilisateur> listJurysByEvent(int idevent) {
		return juryDao.listJurysByEvent(idevent);
	}
	@Override
	public List<Jury> listJurysAnnonymesByEvent(int idevent) {
		return juryDao.findAnonymousByIdEvent(idevent);
	}
	@Override
	public List<Utilisateur> findAllJurys(){
		return userDao.getAllUsers();
	}
	@Override
	public void supprimerUtilisateur(int iduser, int idevent) {
		juryDao.supprimerUtilisateur(iduser,idevent);
	}
	@Override
	public void addJuryToEvent(Jury jury) {
		// don't add jury if assigned
		if (juryDao.juryIsAssigned(jury.getUtilisateur().getIduser(), jury.getEvenement().getIdevent())) {
			return;
		}
		juryDao.addJuryToEvent(jury);
	}
	@Override
	public List<Jury> findAnonymousByIdEvent(int idEvent) {
		return juryDao.findAnonymousByIdEvent(idEvent);
	}
	@Override
	public void addListJurys(List<Jury> jurys) {
		juryDao.addListJurys(jurys);
	}
	@Override
	public Utilisateur findById(int id) {
		return juryDao.findById(id);
	}
	@Override
	public Jury findJuryById(int id) {
		return juryDao.findJuryById(id);
	@Override
	public void supprimerUtilisateurAnonyme(int iduser) {
		juryDao.supprimerUtilisateurAnonyme(iduser);

	}
}
