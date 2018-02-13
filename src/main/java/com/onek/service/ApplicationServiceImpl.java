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
import com.onek.dao.GrilleDao;
import com.onek.dao.JuryDao;
import com.onek.dao.LoginDao;
import com.onek.dao.NoteDao;
import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Evaluation;
import com.onek.model.Jury;
import com.onek.model.Note;
import com.onek.resource.CandidatResource;
import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;
import com.onek.resource.JuryResource;
import com.onek.resource.NoteResource;

@Service
public class ApplicationServiceImpl implements ApplicationService, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EvenementDao eventDao;

	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private JuryDao juryDao;
	
	@Autowired
	private GrilleDao critereDao;
	
	@Autowired
	private EvaluationDao evaluationDao;
	
	@Autowired
	private NoteDao noteDao;

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
		if (!juryDao.juryIsAssigned(idJury, id)) {
			return Optional.empty();
		}

		try {			
			EvenementResource eventResource = new EvenementResource(eventDao.findById(id));
			List<Jury> jurys = juryDao.findJuryAndAnonymousByIdEvent(id, login);
			List<EvaluationResource> evaluations = createEvaluationList(jurys);		
			
			eventResource.setEvaluations(evaluations);			
			eventResource.setJurys(associatedJurysCandidates(jurys));
			return Optional.of(eventResource);
		} catch (NoResultException nre) {
			return Optional.empty();
		}
	}	

	/* associated jurys and candidates for an event */
	private List<JuryResource> associatedJurysCandidates(List<Jury> jurys) {
		HashMap<Jury, List<Candidat>> map = new HashMap<>();
		List<JuryResource> jurysResource = new ArrayList<>();	
		
		for(Jury jury : jurys) {			
			if (!map.containsKey(jury)) {
				map.put(jury, new ArrayList<>());
			}
			
			List<Evaluation> evaluations = jury.getEvaluations();
			for (Evaluation evaluation : evaluations) {
				List<Candidat> candidates = map.get(jury);
				candidates.add(evaluation.getCandidat());
				map.put(jury, candidates);
			}			
		}	

		// create jury list with map
		for (Entry<Jury, List<Candidat>> mapEntry : map.entrySet()) {
			JuryResource jury = new JuryResource(mapEntry.getKey());
			
			List<Candidat> candidats = mapEntry.getValue();
			List<CandidatResource> candidatRessource = new ArrayList<>();		
			
			for(Candidat candidat : candidats) {
				candidatRessource.add(new CandidatResource(candidat));
			}			
			jury.setCandidates(candidatRessource);		
			
			jurysResource.add(jury);
		}

		return jurysResource;
	}
	
	/* create evaluation list */
	private List<EvaluationResource> createEvaluationList(List<Jury> jurys) {
		List<EvaluationResource> evaluations = new ArrayList<>();		
		for(Jury jury : jurys) {
			List<Evaluation> evaluationForOneJury = jury.getEvaluations();			
			for(Evaluation evaluation : evaluationForOneJury) {
				evaluations.add(new EvaluationResource(evaluation));
			}						
		}		
		return evaluations;
	}

	@Override
	public EvaluationResource importEvaluation(EvaluationResource evaluationResource) {
		List<NoteResource> noteResources = evaluationResource.getNotes();
		for(NoteResource noteResource : noteResources) {
			Integer idNote = noteResource.getIdNote();								
			Integer idEvaluation = evaluationResource.getIdEvaluation();
			
			Note note =	noteResource.createNote();				
			Evaluation evaluation = evaluationDao.findById(idEvaluation);
			
			// check last update date
			if (evaluationResource.getDateLastChange().getTime() < evaluation.getDatedernieremodif().getTime()) {
				return evaluationResource;
			}		
			
			Critere critere = critereDao.findById(noteResource.getIdCriteria());
			note.setEvaluation(evaluation);
			note.setCritere(critere);					
			
			// note create by application
			if (idNote == 0) {
				note = noteDao.addNote(note);	
				noteResource.setIdNote(note.getIdnote());
			}
			// update
			else {
				note.setIdnote(noteResource.getIdNote());
				noteDao.update(note);
			}			
		}
		return evaluationResource;
	}

}
