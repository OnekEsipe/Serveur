package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvaluationDao;
import com.onek.model.Candidat;
import com.onek.model.Evaluation;
import com.onek.model.Jury;

@Service
public class EvaluationServiceImpl implements EvaluationService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EvaluationDao evaluationDao;
  

	@Override
	public List<Evaluation> findByIdCandidate(Integer idCandidat) {
		return evaluationDao.findByIdCandidate(idCandidat);
	}
  
  @Override
	public void deleteEvaluation(int idJury, int idCandidat) {
		evaluationDao.deleteEvaluation(idJury, idCandidat);
	}
	
	@Override
	public void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury) {
		evaluationDao.saveEvaluation(nomCandidat, prenomCandidat, idevent, jury);
	}

	@Override
	public void saveEvaluation(Candidat candidat, Jury jury) {
		evaluationDao.saveEvaluation(candidat, jury);
	}
}
