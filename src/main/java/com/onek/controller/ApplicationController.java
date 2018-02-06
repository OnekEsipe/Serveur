package com.onek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value = "/events/export", method = RequestMethod.GET)
	public ResponseEntity<String> export(/*@RequestBody*/) {
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
}
