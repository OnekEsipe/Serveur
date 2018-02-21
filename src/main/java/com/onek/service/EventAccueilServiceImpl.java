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
	public void editEvenement(Evenement event) {
		eventDao.editEvenement(event);
	}
}
