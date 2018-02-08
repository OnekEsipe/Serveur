package com.onek.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvaluationDao;
import com.onek.dao.EvenementDao;
import com.onek.dao.LoginDao;
import com.onek.model.Candidat;
import com.onek.model.Evaluation;
import com.onek.model.Utilisateur;
import com.onek.resource.EvenementResource;
import com.onek.resource.JuryResource;

@Service
public class ApplicationServiceImpl implements ApplicationService, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EvenementDao eventDao;
	
	@Autowired
	private EvaluationDao evaluationDao;
	
	@Autowired
	private LoginDao loginDao;

	@Override
	public Optional<EvenementResource> export(String idEvent, String login) {
		// check if idEvent is an integer
		Integer id;		
		try {
			id = Integer.valueOf(idEvent);
		}
		catch(NumberFormatException nfe) {
			return Optional.empty();
		}				
		
		// check if login exist
		if (!loginDao.userExist(login)) {
			return Optional.empty();
		}
		
		try {
			EvenementResource event = new EvenementResource(eventDao.findById(id));
			event.setJurys(associatedJurysCandidates(id, login));
			return Optional.of(event);					
		}
		catch(NoResultException nre) {
			return Optional.empty();
		}	
	}
	
	/* associated jurys and candidates for an event */
	private List<JuryResource> associatedJurysCandidates(Integer idEvent, String login) {
		HashMap<Utilisateur, List<Candidat>> map = new HashMap<>();
		List<JuryResource> jurys = new ArrayList<>();
		
		List<Evaluation> evaluations = evaluationDao.findByIdEvent(idEvent);
		
		/* associate jury with its candidates */
		for(Evaluation evaluation : evaluations) {
			Utilisateur user = evaluation.getUtilisateur();
			
			if (!map.containsKey(user)) { 
				map.put(user, new ArrayList<>());
			}
			
			List<Candidat> candidates = map.get(user);
			candidates.add(evaluation.getCandidat());
			map.put(user, candidates);			
		}
		
		// create jury list with map
		//Set<Entry<Utilisateur, List<Candidat>>> set = map.entrySet();
		
	}

}
