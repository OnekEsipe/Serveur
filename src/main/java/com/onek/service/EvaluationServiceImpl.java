package com.onek.service;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvaluationDao;
import com.onek.model.Candidat;
import com.onek.model.Evaluation;
import com.onek.model.Jury;

/**
 * Service EvaluationServiceImpl
 */
@Service
public class EvaluationServiceImpl implements EvaluationService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EvaluationDao evaluationDao;
  

	@Override
	public List<Evaluation> findByIdCandidate(int idCandidat) {
		return evaluationDao.findByIdCandidate(idCandidat);
	}
  
  @Override
	public void deleteEvaluation(int idJury, int idCandidat) {
		evaluationDao.deleteEvaluation(idJury, idCandidat);
	}

	@Override
	public void saveEvaluation(Candidat candidat, Jury jury, Date date, int idevent) {
		evaluationDao.saveEvaluation(candidat, jury, date, idevent);
	}

	@Override
	public List<Evaluation> findByIdJury(int idJury) {
		return evaluationDao.findByIdJury(idJury);
	}

	@Override
	public Evaluation findById(int id) {
		return evaluationDao.findById(id);
	}

	@Override
	public void update(Evaluation evaluation) {
		evaluationDao.update(evaluation);
		
	}
}
