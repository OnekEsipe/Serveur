package com.onek.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.CandidatesDao;
import com.onek.dao.EvaluationDao;
import com.onek.dao.JuryDao;
import com.onek.model.Candidat;
import com.onek.model.Jury;

@Service
public class EvaluationServiceImpl implements EvaluationService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EvaluationDao evaluationdao;
	
	@Override
	public void saveEvaluation(Candidat candidat, Jury jury) {
		evaluationdao.saveEvaluation(candidat, jury);
	}
}
