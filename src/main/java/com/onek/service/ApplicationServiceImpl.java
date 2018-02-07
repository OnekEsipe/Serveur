package com.onek.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvenementDao;
import com.onek.model.Evenement;

@Service
public class ApplicationServiceImpl implements ApplicationService, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EvenementDao eventDao;
	
	@Override
	public List<Integer> parser(String ids) {				
		if (ids != null && (!ids.startsWith("[") || !ids.endsWith("]"))) {
			throw new IllegalArgumentException();
		}
		
		ids = ids.substring(1, ids.length() - 1);			
		
		List<String> listIds = Arrays.asList(ids.split(","));
		List<Integer> idEvents = new ArrayList<>();
		for(String id : listIds) {
			try {
				idEvents.add(Integer.valueOf(id));
			}
			catch(NumberFormatException nfe) {
				throw new IllegalArgumentException();
			}
		}
		
		return idEvents;		
	}

	@Override
	public Evenement export(List<Integer> idEvents) {
		List<Integer> eventsNotFound = new ArrayList<>();		
		
		for(Integer id : idEvents) {
			try {
				Evenement event = eventDao.findById(id);				
				return event;
				
			}
			catch(NoResultException nre) {
				eventsNotFound.add(id);
			}			
		}		
		
		if (eventsNotFound.size() > 0) {
			System.out.println("Events not found : " + eventsNotFound);
			throw new IllegalStateException();
		}	
		
		return null;
	}

}
