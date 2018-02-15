package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvaluationDao;

@Service
public class EvaluationServiceImpl implements EvaluationService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EvaluationDao evaluationDao;
	
	@Override
	public void deleteEvaluation(int idJury, int idCandidat) {
		evaluationDao.deleteEvaluation(idJury, idCandidat);
	}
	
}
