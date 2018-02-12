package com.onek.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;
import com.onek.service.ApplicationService;

@RestController
@RequestMapping("/app")
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;

	/* Create a new jury */
	@RequestMapping(value = "/createjury", method = RequestMethod.POST)
	public ResponseEntity<String> createJury(/*@RequestBody Utilisateur user*/) {
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/* evaluation */
	@RequestMapping(value = "/evaluation", method = RequestMethod.POST)
	public ResponseEntity<?> evaluation(@RequestBody EvaluationResource evaluation) {		
		evaluation = applicationService.importEvaluation(evaluation);		
		return new ResponseEntity<EvaluationResource>(evaluation, HttpStatus.OK);
	}
	
	/* event export */
	@RequestMapping(value = "/events/{idEvent}/{login}/export", method = RequestMethod.GET)
	public ResponseEntity<?> export(@PathVariable String idEvent, @PathVariable String login) {			
		Optional<EvenementResource> event = applicationService.export(idEvent, login);
		
		if (!event.isPresent()) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}	
		
		return new ResponseEntity<EvenementResource>(event.get(), HttpStatus.OK);
	}		
}
