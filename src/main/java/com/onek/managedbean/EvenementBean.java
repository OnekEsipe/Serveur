package com.onek.managedbean;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.onek.model.Evenement;
import com.onek.service.EvenementService;

public class EvenementBean implements Serializable {
	private static final long serialVersionUID = 1L;	
	
	@Autowired
	EvenementService evenementService;
	
	private Evenement event;
	
	public EvenementBean() {
		event = new Evenement();		
	}
	
	public void addEvent(Evenement event) {
		evenementService.addEvenement(event);
	}	
}
