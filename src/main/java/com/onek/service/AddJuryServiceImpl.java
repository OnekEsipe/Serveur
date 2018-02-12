package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.AddJuryDao;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;

@Service
public class AddJuryServiceImpl implements AddJuryService, Serializable{
private static final long serialVersionUID = 1L;
	
	@Autowired
	private AddJuryDao addjuryDao;
	@Override
	public List<Utilisateur> listJurysByEvent(int idevent) {
		return addjuryDao.listJurysByEvent(idevent);
	}
	@Override
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent) {
		return addjuryDao.listJurysAnnonymesByEvent(idevent);
	}
	@Override
	public List<Utilisateur> listJurysAll(){
		return addjuryDao.listJurysAll();
	}
	@Override
	public void supprimerUtilisateur(int iduser) {
		addjuryDao.supprimerUtilisateur(iduser);
	}
	@Override
	public void addJuryToEvent(Jury jury) {
		addjuryDao.addJuryToEvent(jury);
	}
	
}
