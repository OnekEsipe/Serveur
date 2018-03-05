package com.onek.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.JuryDao;
import com.onek.dao.UserDao;
import com.onek.model.Candidat;
import com.onek.model.Evaluation;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;

/**
 * Service JuryServiceImpl
 */
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
		Objects.requireNonNull(jurys);
		if(idevent < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		if(jurys.isEmpty()) {
			throw new IllegalStateException("List must not be empty");
		}
		HashMap<Jury, List<Candidat>> map = new HashMap<>();
		for (Jury jury : jurys) {
			if (!map.containsKey(jury)) {
				map.put(jury, new ArrayList<>());
			}
			List<Evaluation> evaluations = jury.getEvaluations();
			for (Evaluation evaluation : evaluations) {
				List<Candidat> candidates = map.get(jury);
				if (evaluation.getCandidat().getEvenement().getIdevent() == idevent) {
					candidates.add(evaluation.getCandidat());
				}
				map.put(jury, candidates);
			}
		}
		return map;
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
	}
	@Override
	public void supprimerUtilisateurAnonyme(int iduser) {
		juryDao.supprimerUtilisateurAnonyme(iduser);

	}

	@Override
	public List<Jury> findJuryAndAnonymousByIdEvent(int idEvent, String login) {
		return juryDao.findJuryAndAnonymousByIdEvent(idEvent, login);
	}

	@Override
	public List<Jury> findByUser(Utilisateur user) {
		return juryDao.findByUser(user);
	}

	@Override
	public List<Utilisateur> findJurysAnnonymesByEvent(int idevent) {
		return juryDao.findJurysAnnonymesByEvent(idevent);
	}
}
