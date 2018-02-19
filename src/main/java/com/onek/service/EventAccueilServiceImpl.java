package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.CandidatesDao;
import com.onek.dao.EvenementDao;
import com.onek.dao.JuryDao;
import com.onek.model.Candidat;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

@Service
public class EventAccueilServiceImpl implements EventAccueilService, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private CandidatesDao candidateDao;

	@Autowired
	private JuryDao juryDao;
	
	@Autowired 
	private EvenementDao eventDao;

	@Override
	public List<Candidat> listCandidatsByEvent(int idevent) {
		return candidateDao.findCandidatesByEvent(idevent);
	}

	@Override
	public List<Utilisateur> listJurysByEvent(int idevent) {
		return juryDao.listJurysByEvent(idevent);
	}

	@Override
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent) {
		return juryDao.findJurysAnnonymesByEvent(idevent);
	}

	@Override
	public void supprimerCandidat(int idcandidat) {
		candidateDao.supprimerCandidat(idcandidat);
	}

	public void supprimerUtilisateur(int iduser) {
		juryDao.supprimerUtilisateur(iduser);
	}

	@Override
	public void editEvenement(Evenement event) {
		eventDao.editEvenement(event);
	}
}
