package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.AccueilDao;
import com.onek.model.Evenement;
@Service
public class AccueilServiceImpl implements AccueilService, Serializable{
	private static final long serialVersionUID = 1L;
	@Autowired
	private AccueilDao accueildao;
	
	@Override
	public List<Evenement> listEvents(){
		return accueildao.listEvents();
	}
}
