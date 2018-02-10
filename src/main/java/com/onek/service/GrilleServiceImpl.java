package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.GrilleDao;
import com.onek.model.Critere;

@Service
public class GrilleServiceImpl implements GrilleService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	GrilleDao grilleDao;
	
	@Override
	public void addCriteres(List<Critere> criteres) {
		grilleDao.addCriteres(criteres);		
	}
}
