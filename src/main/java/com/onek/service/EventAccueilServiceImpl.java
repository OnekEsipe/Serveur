package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EventAccueilDao;
import com.onek.model.Candidat;
import com.onek.model.Utilisateur;

@Service
public class EventAccueilServiceImpl implements EventAccueilService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventAccueilDao eventAccueilDao;
	
	@Override
	public List<Candidat> listCandidatsByEvent(int idevent){
		return eventAccueilDao.listCandidatsByEvent(idevent);
	}

	@Override
	public List<Utilisateur> listJurysByEvent(int idevent) {
		return eventAccueilDao.listJurysByEvent(idevent);
	}
	@Override
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent) {
		return eventAccueilDao.listJurysAnnonymesByEvent(idevent);
	}
	@Override
	public void supprimerCandidat(int idcandidat) {
		eventAccueilDao.supprimerCandidat(idcandidat);
	}
}
