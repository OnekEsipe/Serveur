package com.onek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onek.model.Evenement;
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
	
	@RequestMapping(value = "/events/{idEvent}/evaluation", method = RequestMethod.POST)
	public ResponseEntity<String> evaluation(@PathVariable String idEvent/*, @RequestBody Evaluation evaluation*/) {
		System.out.println(idEvent);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/* event export */
	@RequestMapping(value = "/events/export", method = RequestMethod.POST)
	public ResponseEntity<?> export(@RequestBody String filenames) {
		List<String> filenamesList;
		
		/* get filenames */
		try {
			filenamesList = applicationService.parser(filenames);
		} 
		catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		/* create the response body */
		Evenement event;
		try {
			event = applicationService.export(filenamesList);
		}
		catch(IllegalStateException e) {
			return new ResponseEntity<String>("test", HttpStatus.PARTIAL_CONTENT);
		}
		
		return new ResponseEntity<Evenement>(event, HttpStatus.OK);
	}
	
	
}
