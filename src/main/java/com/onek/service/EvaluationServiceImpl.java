package com.onek.service;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvaluationDao;
import com.onek.model.Jury;

@Service
public class EvaluationServiceImpl implements EvaluationService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EvaluationDao evaluationDao;
	
	@Override
	public void deleteEvaluation(int idJury, int idCandidat) {
		evaluationDao.deleteEvaluation(idJury, idCandidat);
	}
	
	@Override
	public void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury, Date date) {
		evaluationDao.saveEvaluation(nomCandidat, prenomCandidat, idevent, jury, date);
	}
	
}
