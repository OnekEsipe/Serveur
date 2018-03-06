package com.onek.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.CritereDao;
import com.onek.dao.EvaluationDao;
import com.onek.dao.EvenementDao;
import com.onek.dao.JuryDao;
import com.onek.dao.NoteDao;
import com.onek.dao.SignatureDao;
import com.onek.model.Candidat;
import com.onek.model.Evaluation;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Note;
import com.onek.model.Signature;
import com.onek.model.Utilisateur;
import com.onek.resource.AccountResource;
import com.onek.resource.CandidatResource;
import com.onek.resource.CodeEvenementResource;
import com.onek.resource.CreateJuryResource;
import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;
import com.onek.resource.JuryResource;
import com.onek.resource.NoteResource;
import com.onek.resource.PasswordModify;
import com.onek.resource.SignatureResource;
import com.onek.utils.DroitsUtilisateur;
import com.onek.utils.Encode;
import com.onek.utils.StatutEvenement;

@Service
public class ApplicationServiceImpl implements ApplicationService, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private EvenementDao eventDao;

	@Autowired
	private JuryDao juryDao;

	@Autowired
	private CritereDao critereDao;

	@Autowired
	private EvaluationDao evaluationDao;

	@Autowired
	private NoteDao noteDao;

	@Autowired
	private UserService userService;

	@Autowired
	private JuryService juryService;

	@Autowired
	private SignatureDao signatureDao;

	@Override
	public Optional<EvenementResource> export(String idEvent, String login) {
		Objects.requireNonNull(idEvent);
		Objects.requireNonNull(login);
		// check if idEvent is an integer
		Integer id;
		try {
			id = Integer.valueOf(idEvent);
		} catch (NumberFormatException nfe) {
			return Optional.empty();
		}
		// check if login exist
		if (!userService.userExist(login)) {
			return Optional.empty();
		}
		Utilisateur user = userService.findByLogin(login);
		// check if user is deleted
		if (user.getIsdeleted()) {
			return Optional.empty();
		}
		// check if jury is assigned
		int idUser = user.getIduser();
		if (!juryDao.juryIsAssigned(idUser, id)) {
			return Optional.empty();
		}
		try {
			Evenement event = eventDao.findById(id);
			if (!eventIsEligible(event)) {
				return Optional.empty();
			}
			EvenementResource eventResource = new EvenementResource(event);
			List<Jury> jurys = juryDao.findJuryAndAnonymousByIdEvent(id, login);
			List<EvaluationResource> evaluations = createEvaluationList(jurys, login);
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
		Objects.requireNonNull(login);
		Utilisateur user = userService.findByLogin(login);	
		List<Jury> jurys = juryDao.findByUser(user);
		List<AccountResource> accounts = new ArrayList<>();
		// search idEvents for the login
		List<Integer> idEvents = new ArrayList<>();
		for (Jury jury : jurys) {
			Evenement event = jury.getEvenement();
			if (eventIsEligible(event, jury)) {
				idEvents.add(event.getIdevent());
			}
		}	
		// hash password for anonymous jury
		if (user.getDroits().equals(DroitsUtilisateur.ANONYME.toString())) {
			try {
				user.setMotdepasse(Encode.sha1(user.getMotdepasse()));
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				logger.error(this.getClass().getName(), e);				
			}
		}
		Collections.sort(idEvents);
		accounts.add(new AccountResource(user, idEvents));
		// search anonymous for all events affected at login
		for (Jury jury : jurys) {
			Evenement event = jury.getEvenement();
			if (!eventIsEligible(event, jury)) {
				continue;
			}
			Integer idEvent = event.getIdevent();
			List<Jury> anonymous = juryDao.findAnonymousByIdEvent(idEvent);
			for (Jury anonym : anonymous) {
				Utilisateur anonymUser = anonym.getUtilisateur();
				try {
					anonymUser.setMotdepasse(Encode.sha1(anonymUser.getMotdepasse()));
				} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
					logger.error(this.getClass().getName(), e);
					continue;
				}
				List<Integer> idEventsAnonym = new ArrayList<>();
				idEventsAnonym.add(idEvent);
				accounts.add(new AccountResource(anonym.getUtilisateur(), idEventsAnonym));
			}
		}
		return accounts;
	}

	@Override
	public boolean importEvaluation(EvaluationResource evaluationResource) {
		if (evaluationResource == null) {
			return false;
		}
		Evaluation evaluation = evaluationDao.findById(evaluationResource.getIdEvaluation());
		if (evaluation == null) {
			return false;
		}
		// check if user associated with a jury is deleted
		if (evaluation.getJury().getUtilisateur().getIsdeleted()) {
			return false;
		}
		Evenement event = eventDao.findById(evaluationResource.getIdEvent());
		if (event == null) {
			return false;
		}
		// check if event if deleted
		if (event.getIsdeleted()) {
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

		// add signature
		if (evaluationResource.getIsSigned() && !evaluation.getIssigned()) {
			evaluation.setIssigned(true);
			for (SignatureResource signatureResource : evaluationResource.getSignatures()) {
				Signature signature = signatureResource.createSignature();
				signature.setEvaluation(evaluation);
				signatureDao.addSignature(signature);
			}
		}
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
	public void createJury(CreateJuryResource createJuryResource) {
		Objects.requireNonNull(createJuryResource);
		Utilisateur user = new Utilisateur();
		user.setPrenom(createJuryResource.getFirstname());
		user.setNom(createJuryResource.getLastname());
		user.setMail(createJuryResource.getMail());
		user.setLogin(createJuryResource.getLogin());
		user.setMotdepasse(createJuryResource.getPassword());
		user.setDroits(DroitsUtilisateur.JURY.toString());
		user.setIsdeleted(false);
		userService.addUser(user);
	}

	@Override
	public boolean subscribe(CodeEvenementResource eventCode) {
		if (!userService.userExist(eventCode.getLogin())) {
			return false;
		}
		Utilisateur user = userService.findByLogin(eventCode.getLogin());
		if (user.getIsdeleted()) {
			return false;
		}
		Evenement event = eventDao.findByCode(eventCode.getEventCode());
		if (event == null) {
			throw new IllegalStateException("Le code événement est incorrect.");
		}
		if (!event.getIsopened()) {
			throw new IllegalStateException("L'événement n'est pas ouvert à l'inscription.");
		}
		if (event.getIsdeleted()) {
			throw new IllegalArgumentException("L'événement n'existe pas.");
		}
		if (event.getStatus().equals(StatutEvenement.FERME.toString())) {
			throw new IllegalArgumentException("L'événement est fermé.");
		}
		if (juryDao.juryIsAssigned(user.getIduser(), event.getIdevent())) {
			throw new IllegalStateException("Vous êtes déjà inscrit.");
		}
		Jury jury = new Jury();
		jury.setUtilisateur(user);
		jury.setEvenement(event);
		juryService.addJuryToEvent(jury);
		return true;
	}

	@Override
	public boolean changePassword(PasswordModify passwordModify) {
		Objects.requireNonNull(passwordModify);
		String login = passwordModify.getLogin();
		if (!userService.userExist(login)) {
			return false;
		}
		Utilisateur user = userService.findByLogin(login);
		if (user.getDroits().equals(DroitsUtilisateur.ANONYME.toString())) {
			return false;
		}
		if (!user.getMotdepasse().equals(passwordModify.getOldPassword())) {
			return false;
		}
		user.setMotdepasse(passwordModify.getNewPassword());
		userService.updateUserInfos(user);
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
	private List<EvaluationResource> createEvaluationList(List<Jury> jurys, String login) {
		List<EvaluationResource> evaluations = new ArrayList<>();
		for (Jury jury : jurys) {
			if (!jury.getUtilisateur().getLogin().equals(login)) {
				continue;
			}
			List<Evaluation> evaluationForOneJury = jury.getEvaluations();
			for (Evaluation evaluation : evaluationForOneJury) {
				evaluations.add(new EvaluationResource(evaluation));
			}
		}
		return evaluations;
	}

	private boolean eventIsEligible(Evenement event, Jury jury) {
		if (!eventIsEligible(event))
			return false;
		// check if jury is assigned to an evaluation
		List<Evaluation> evaluations = evaluationDao.findByIdJury(jury.getIdjury());
		if (evaluations.isEmpty()) {
			return false;
		}
		return true;
	}

	private boolean eventIsEligible(Evenement event) {
		// check if evenement is deleted
		if (event.getIsdeleted()) {
			return false;
		}
		// check if evenement is opened
		if (!event.getStatus().equals(StatutEvenement.OUVERT.toString())) {
			return false;
		}
		// check if end date of event > date today (status stopped)
		if ((event.getDatestop().getTime() < new Date().getTime())) {
			return false;
		}
		return true;
	}
}
