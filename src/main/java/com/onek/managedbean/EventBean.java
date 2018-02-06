package com.onek.managedbean;

import org.springframework.beans.factory.annotation.Autowired;

import com.onek.service.EventService;

public class EventBean {
	private static final long serialVersionUID = 1L;	
	
	@Autowired
	EventService eventService;
	
	private Event event;
	
	public EventBean() {
		event = new Event();		
	}
	
	public void addEvent(Event event) {
		eventService.addEvent(event);
	}	

}
