package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.onek.dao.EventDao;

public class EventServiceImpl implements EventService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EventDao eventDao;
	
	@Override
	public void addEvent(Event event) {
		eventDao.addEvent(event);		
	}

}
