package com.onek.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import com.onek.service.ApplicationService;
import com.onek.service.UserService;

@RestController
@RequestMapping("/app")
public class ApplicationController {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private UserService userService;

	/* Create a new jury from app */
	@RequestMapping(value = "/createjury", method = RequestMethod.POST)
	public ResponseEntity<String> createJury(@RequestBody CreateJuryResource createJuryResource) {
		try {			
			applicationService.createJury(createJuryResource);		
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {			
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch(IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/* login */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody LoginResource login) {
		try {
			if (!userService.userExistAndCorrectPassword(login.getLogin(), login.getPassword())) {
				return new ResponseEntity<LoginResource>(login, HttpStatus.FORBIDDEN);
			}
		}
		catch(IllegalStateException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<AccountResource> accounts = applicationService.account(login.getLogin());		
		return new ResponseEntity<List<AccountResource>>(accounts, HttpStatus.OK);
	}
	
	/* import evaluation */
	@RequestMapping(value = "/evaluation", method = RequestMethod.POST)
	public ResponseEntity<?> evaluation(@RequestBody EvaluationResource evaluation) {
		for(NoteResource noteResource : evaluation.getNotes()) {
			if (noteResource.getSelectedLevel().length() != 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		if (!applicationService.importEvaluation(evaluation)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();	
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/* event export */
	@RequestMapping(value = "/events/{idEvent}/{login}/export", method = RequestMethod.GET)
	public ResponseEntity<?> export(@PathVariable String idEvent, @PathVariable String login) {			
		Optional<EvenementResource> event = applicationService.export(idEvent, login);		
		if (!event.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}			
		return new ResponseEntity<EvenementResource>(event.get(), HttpStatus.OK);
	}	
	
	/* code event */
	@RequestMapping(value = "/events/code", method = RequestMethod.POST)
	public ResponseEntity<?> codeEvent(@RequestBody CodeEvenementResource eventCode) {
		if (!applicationService.subscribe(eventCode)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return  ResponseEntity.status(HttpStatus.OK).build();
	}
}
