package com.onek.managedbean;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Evenement;
import com.onek.service.EvenementService;

@Component("eventBean")
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
