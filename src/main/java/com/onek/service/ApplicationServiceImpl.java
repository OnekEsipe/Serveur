package com.onek.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
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
import com.onek.model.Evaluation;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Note;
import com.onek.model.Utilisateur;
import com.onek.resource.AccountResource;
import com.onek.resource.CandidatResource;
import com.onek.resource.CodeEvenementResource;
import com.onek.resource.CreateJuryResource;
import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;
import com.onek.resource.JuryResource;
import com.onek.resource.NoteResource;
import com.onek.utils.EncodePassword;
import com.onek.utils.StatutEvenement;

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

	@Autowired
	private UserService userService;

	@Autowired
	private AddJuryService addJuryService;

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
		int idUser = loginDao.findUserByLogin(login).getIduser();
		if (!juryDao.juryIsAssigned(idUser, id)) {
			return Optional.empty();
		}
		try {
			Evenement event = eventDao.findById(id);
			// check if evenement is opened
			if (!event.getStatus().equals(StatutEvenement.OUVERT.toString())) {
				return Optional.empty();
			}
			// check if end date of event > date today (status stopped)
			if ((event.getDatestop().getTime() < new Date().getTime())) {
				return Optional.empty();
			}
			EvenementResource eventResource = new EvenementResource(event);
			List<Jury> jurys = juryDao.findJuryAndAnonymousByIdEvent(id, login);
			List<EvaluationResource> evaluations = createEvaluationList(jurys);
			eventResource.setEvaluations(evaluations);
			eventResource.setJurys(associatedJurysCandidates(jurys, id));
			return Optional.of(eventResource);
		} catch (NoResultException nre) {
			return Optional.empty();
		}
	}	

	/* account */
	@Override
	public List<AccountResource> account(String login) {
		Utilisateur user = loginDao.findUserByLogin(login);
		List<Jury> jurys = juryDao.findByUser(user);
		List<AccountResource> accounts = new ArrayList<>();
		// search idEvents for the login
		List<Integer> idEvents = new ArrayList<>();
		for (Jury jury : jurys) {
			idEvents.add(jury.getEvenement().getIdevent());
		}
		Collections.sort(idEvents);
		accounts.add(new AccountResource(user, idEvents));
		// search anonymous for all events affected at login
		for (Jury jury : jurys) {
			Integer idEvent = jury.getEvenement().getIdevent();
			List<Jury> anonymous = juryDao.findAnonymousByIdEvent(idEvent);
			for (Jury anonym : anonymous) {
				List<Integer> idEventsAnonym = new ArrayList<>();
				idEventsAnonym.add(idEvent);
				accounts.add(new AccountResource(anonym.getUtilisateur(), idEventsAnonym));
			}
		}
		return accounts;
	}

	@Override
	public boolean importEvaluation(EvaluationResource evaluationResource) {
		Evaluation evaluation = evaluationDao.findById(evaluationResource.getIdEvaluation());
		if (evaluation == null) {
			return false;
		}
		Evenement event = eventDao.findById(evaluationResource.getIdEvent());
		if (event == null) {
			return false;
		}		
		// check if event if opened
		if (!event.getStatus().equals(StatutEvenement.OUVERT.toString())) {
			return false;
		}
		long dateLastChangeNewEval = evaluationResource.getDateLastChange().getTime();
		long dateLastChangeEvalDB = evaluation.getDatedernieremodif().getTime();
		// check last update date for evaluation
		if (dateLastChangeNewEval < dateLastChangeEvalDB) {
			throw new IllegalStateException();
		}
		// active check date if necessary
		boolean checkDate = false;
		if (dateLastChangeNewEval > dateLastChangeEvalDB && dateLastChangeNewEval > event.getDatestop().getTime()) {			
			checkDate = true;
		}
		evaluation.setCommentaire(evaluationResource.getComment());		
		evaluation.setDatedernieremodif(evaluationResource.getDateLastChange());
		// evaluation.setSignature(evaluationResource.getSignature()); // TODO
		evaluationDao.update(evaluation);
		List<NoteResource> noteResources = evaluationResource.getNotes();
		List<Note> newMarks = new ArrayList<>();
		List<Note> marksDB = evaluation.getNotes();
		// change type mark
		for (NoteResource noteResource : noteResources) {
			Note note = noteResource.createNote();
			note.setEvaluation(evaluation);
			note.setCritere(critereDao.findById(noteResource.getIdCriteria()));
			newMarks.add(note);
		}
		// add or update mark
		for (Note newMark : newMarks) {
			// check if date should be add
			if (checkDate && newMark.getDate().getTime() > event.getDatestop().getTime()) {
				continue;
			}			
			boolean noteIsNotFound = true;
			for (Note markDB : marksDB) {
				// update
				if (markDB.getCritere().getIdcritere() == newMark.getCritere().getIdcritere()) {
					newMark.setIdnote(markDB.getIdnote());
					noteDao.update(newMark);
					noteIsNotFound = false;
					break;
				}
			}
			if (noteIsNotFound) {
				noteDao.addNote(newMark);
			}
		}
		return true;		
	}

	@Override
	public void createJury(CreateJuryResource createJuryResource)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Utilisateur user = new Utilisateur();
		user.setPrenom(createJuryResource.getFirstname());
		user.setNom(createJuryResource.getLastname());
		user.setMail(createJuryResource.getMail());
		user.setLogin(createJuryResource.getLogin());
		user.setMotdepasse(EncodePassword.sha1(createJuryResource.getPassword()));
		user.setDroits("J");
		user.setIsdeleted(false);
		userService.addUser(user);
	}

	@Override
	public boolean subscribe(CodeEvenementResource eventCode) {
		if (!loginDao.userExist(eventCode.getLogin())) {
			return false;
		}
		Utilisateur user = loginDao.findUserByLogin(eventCode.getLogin());
		Evenement event;
		try {
			event = eventDao.findByCode(eventCode.getEventCode());
		} catch (NoResultException rse) {
			return false;
		}
		Jury jury = new Jury();
		jury.setUtilisateur(user);
		jury.setEvenement(event);
		addJuryService.addJuryToEvent(jury);
		return true;
	}	
	
	/* associated jurys and candidates for an event */
	private List<JuryResource> associatedJurysCandidates(List<Jury> jurys, Integer idEvent) {
		HashMap<Jury, List<Candidat>> map = new HashMap<>();
		List<JuryResource> jurysResource = new ArrayList<>();
		for (Jury jury : jurys) {
			if (!map.containsKey(jury)) {
				map.put(jury, new ArrayList<>());
			}
			List<Evaluation> evaluations = jury.getEvaluations();
			for (Evaluation evaluation : evaluations) {
				Candidat candidate = evaluation.getCandidat();
				if (candidate.getEvenement().getIdevent() != idEvent) {
					continue;
				}
				List<Candidat> candidates = map.get(jury);				
				candidates.add(candidate);
				map.put(jury, candidates);
			}
		}
		// create jury list with map
		for (Entry<Jury, List<Candidat>> mapEntry : map.entrySet()) {
			JuryResource jury = new JuryResource(mapEntry.getKey());
			List<Candidat> candidats = mapEntry.getValue();
			List<CandidatResource> candidatRessource = new ArrayList<>();
			for (Candidat candidat : candidats) {
				candidatRessource.add(new CandidatResource(candidat));
			}
			jury.setCandidates(candidatRessource);
			jurysResource.add(jury);
		}
		return jurysResource;
	}

	/* create evaluation resource list */
	private List<EvaluationResource> createEvaluationList(List<Jury> jurys) {
		List<EvaluationResource> evaluations = new ArrayList<>();
		for (Jury jury : jurys) {
			List<Evaluation> evaluationForOneJury = jury.getEvaluations();
			for (Evaluation evaluation : evaluationForOneJury) {
				evaluations.add(new EvaluationResource(evaluation));
			}
		}
		return evaluations;
	}
}
