package com.onek.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvenementDao;

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
	public void export(List<String> filenames) {
		
		filenames.forEach(name -> {
			try {
				eventDao.findByName(name);
			}
			catch(NoResultException nre) {
				throw new IllegalStateException();
			}
		});		
		
	}

}
