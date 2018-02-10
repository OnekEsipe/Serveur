package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.AttributionJCDao;
import com.onek.model.Candidat;
import com.onek.model.Utilisateur;

@Service
public class AttributionJCServiceImpl implements AttributionJCService, Serializable{
private static final long serialVersionUID = 1L;
	
	@Autowired
	private AttributionJCDao attributionjcDao;
	
	@Override
	public List<Candidat> listCandidatsByEvent(int idevent){
		return attributionjcDao.listCandidatsByEvent(idevent);
	}

	@Override
	public List<Utilisateur> listJurysByEvent(int idevent) {
		return attributionjcDao.listJurysByEvent(idevent);
	}
}
