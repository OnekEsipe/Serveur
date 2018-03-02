package com.onek.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onek.resource.AccountResource;
import com.onek.resource.CodeEvenementResource;
import com.onek.resource.CreateJuryResource;
import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;
import com.onek.resource.LoginResource;
import com.onek.resource.NoteResource;
import com.onek.resource.PasswordModify;
import com.onek.resource.PasswordResetResource;
import com.onek.service.ApplicationService;
import com.onek.service.PasswordService;
import com.onek.service.UserService;

/**
 * Controller de l'application web. Cette classe définit les routes rest.
 */
@RestController
@RequestMapping("/app")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordService passwordService;

	/**
	 * Route rest pour la création d'un nouveau jury depuis l'application mobile.
	 * @param createJuryResource
	 * @return 200 si OK <br/> 409 si CONFLICT 
	 */
	@RequestMapping(value = "/createjury", method = RequestMethod.POST)
	public ResponseEntity<String> createJury(@RequestBody CreateJuryResource createJuryResource) {
		try {
			applicationService.createJury(createJuryResource);
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * Route pour s'identifier depuis l'application mobile
	 * @param login
	 * @return 403 FORBIDDEN si l'authentification échoue <br/> 
	 * 500 INTERNAL_SERVER_ERROR si IllegalStateException <br/>
	 * OK 200 si l'authentification aboutie
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<? extends Object> login(@RequestBody LoginResource login) {
		try {
			if (!userService.userExistAndCorrectPassword(login.getLogin(), login.getPassword())) {
				return new ResponseEntity<LoginResource>(login, HttpStatus.FORBIDDEN);
			}
		} catch (IllegalStateException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<AccountResource> accounts = applicationService.account(login.getLogin());
		return new ResponseEntity<List<AccountResource>>(accounts, HttpStatus.OK);
	}

	/**
	 * Route pour importer une évaluation depuis l'application mobile
	 * @param evaluation
	 * @return 400 BAD REQUEST si l'import échoue <br/>
	 * 409 CONFLICT si IllegalStateException <br/>
	 * OK 200 si l'évaluation est bien importée
	 */
	@RequestMapping(value = "/evaluation", method = RequestMethod.POST)
	public ResponseEntity<? extends Object> evaluation(@RequestBody EvaluationResource evaluation) {
		for (NoteResource noteResource : evaluation.getNotes()) {
			if (noteResource.getSelectedLevel().length() > 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		try {
			if (!applicationService.importEvaluation(evaluation)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * Route pour exporter un événement depuis l'application mobile
	 * @param idEvent
	 * @param login
	 * @return 404 NOT FOUND si aucun événement <br/>
	 * OK 200 si l'événement est exportée
	 */
	@RequestMapping(value = "/events/{idEvent}/{login}/export", method = RequestMethod.GET)
	public ResponseEntity<? extends Object> export(@PathVariable String idEvent, @PathVariable String login) {
		Optional<EvenementResource> event = applicationService.export(idEvent, login);
		if (!event.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return new ResponseEntity<EvenementResource>(event.get(), HttpStatus.OK);
	}

	/**
	 * Route pour pouvoir s'inscrire depuis l'application mobile
	 * @param eventCode
	 * @return 403 FORBIDDEN si l'inscription est impossible <br/>
	 * 409 CONFLICT si IllegalStateException <br/>
	 * OK 200 si l'inscription est réalisée
	 */
	@RequestMapping(value = "/events/code", method = RequestMethod.POST)
	public ResponseEntity<? extends Object> codeEvent(@RequestBody CodeEvenementResource eventCode) {
		try {
			if (!applicationService.subscribe(eventCode)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/* ids event */
	/** Route qui permet de récuperer la liste des ids event pour un jury depuis l'application mobile
	 * @param login
	 * @return 403 FORBIDDEN si l'authentification du jury échoue <br/>
	 * 500 INTERNAL_SERVER_ERROR si IllegalStateException <br/>
	 * OK 200 si envoie réalisée
	 */
	@RequestMapping(value = "jury/events/id", method = RequestMethod.POST)
	public ResponseEntity<? extends Object> idsEvents(@RequestBody LoginResource login) {
		try {
			if (!userService.userExistAndCorrectPassword(login.getLogin(), login.getPassword())) {
				return new ResponseEntity<LoginResource>(login, HttpStatus.FORBIDDEN);
			}
		} catch (IllegalStateException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<AccountResource> accounts = applicationService.account(login.getLogin());
		return new ResponseEntity<AccountResource>(accounts.get(0), HttpStatus.OK);
	}

	/**
	 * Route pour modifier son mot de passe depuis l'application mobile.
	 * @param passwordModify
	 * @return 409 CONFLICT si la modification échoue <br/>
	 * OK 200 si modification réalisée
	 */
	@RequestMapping(value = "/password/modify", method = RequestMethod.POST)
	public ResponseEntity<? extends Object> passwordModify(@RequestBody PasswordModify passwordModify) {
		if (!applicationService.changePassword(passwordModify)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * Route pour réinitialiser son mot de passe depuis l'application mobile. Envoie un mail avec un lien muni d'un token.
	 * @param passwordReset
	 * @return 400 BAD REQUEST si le mail est null <br/>
	 * 409 CONFLICT si la réinitialisation échoue <br/>
	 * OK 200 si envoie du mail réalisée
	 */
	@RequestMapping(value = "/password/reset", method = RequestMethod.POST)
	public ResponseEntity<?> passwordReset(@RequestBody PasswordResetResource passwordReset) {
		String mail = passwordReset.getMail();
		if (mail == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		if (!passwordService.reset(mail)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
