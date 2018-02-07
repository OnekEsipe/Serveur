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
	public List<String> parser(String filenames) {				
		if (filenames != null && (!filenames.startsWith("[") || !filenames.endsWith("]"))) {
			throw new IllegalArgumentException();
		}
		
		filenames = filenames.substring(1, filenames.length() - 1);			
		
		return Arrays.asList(filenames.split(","));
	}

	@Override
	public Evenement export(List<String> filenames) {
		List<String> eventsNotFound = new ArrayList<>();		
		
		for(String name : filenames) {
			try {
				Evenement event = eventDao.findByName(name);
				
				System.out.println(event.getCriteres().get(0).getTexte());
				
				return event;
				
			}
			catch(NoResultException nre) {
				eventsNotFound.add(name);
			}			
		}		
		
		if (eventsNotFound.size() > 0) {
			System.out.println("Events not found : " + eventsNotFound);
			throw new IllegalStateException();
		}	
		
		return null;
	}

}
