package com.onek.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
import com.onek.resource.CandidatResource;
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
		} catch (NumberFormatException nfe) {
			return Optional.empty();
		}

		// check if login exist
		if (!loginDao.userExist(login)) {
			return Optional.empty();
		}
		
		// check if jury is assigned
		int idJury = loginDao.findUserByLogin(login).getIduser();
		if (!evaluationDao.juryIsAssigned(idJury, id)) {
			return Optional.empty();
		}

		try {
			EvenementResource event = new EvenementResource(eventDao.findById(id));
			event.setJurys(associatedJurysCandidates(id, login));
			return Optional.of(event);
		} catch (NoResultException nre) {
			return Optional.empty();
		}
	}

	/* associated jurys and candidates for an event */
	private List<JuryResource> associatedJurysCandidates(Integer idEvent, String login) {
		HashMap<Utilisateur, List<Candidat>> map = new HashMap<>();
		List<JuryResource> jurys = new ArrayList<>();

		List<Evaluation> evaluations = evaluationDao.findByIdEvent(idEvent);

		/* associate jury with its candidates */
		for (Evaluation evaluation : evaluations) {
			Utilisateur user = evaluation.getUtilisateur();
			
			// TODO update this when anonyme attribut will be add in Utilisateur class
			//if (!user.isAnonyme())) {
				if (!user.getLogin().equals(login)) {
					continue;
				}
			//}

			if (!map.containsKey(user)) {
				map.put(user, new ArrayList<>());
			}

			List<Candidat> candidates = map.get(user);
			candidates.add(evaluation.getCandidat());
			map.put(user, candidates);
		}

		// create jury list with map
		for (Entry<Utilisateur, List<Candidat>> mapEntry : map.entrySet()) {
			JuryResource jury = new JuryResource(mapEntry.getKey());
			
			List<Candidat> candidats = mapEntry.getValue();
			List<CandidatResource> candidatRessource = new ArrayList<>();		
			
			for(Candidat candidat : candidats) {
				candidatRessource.add(new CandidatResource(candidat));
			}			
			jury.setCandidates(candidatRessource);		
			
			jurys.add(jury);
		}

		return jurys;
	}

}
