package com.onek.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.JuryDao;
import com.onek.model.Candidat;
import com.onek.model.Jury;

@Service
public class JuryServiceImpl implements JuryService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	JuryDao juryDao;
	
	@Override
	public List<Jury> findJuryByIdevent(int idevent) {
		return juryDao.findJuryByIdevent(idevent);
	}
	
	@Override
	public HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent) {
		return juryDao.associatedJurysCandidatesByEvent(jurys, idevent);
	}
}
