package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EvaluationDao;
import com.onek.model.Evaluation;

@Service
public class EvaluationServiceImpl implements EvaluationService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EvaluationDao evaluationDao;

	@Override
	public List<Evaluation> findByIdCandidate(Integer idCandidat) {
		return evaluationDao.findByIdCandidate(idCandidat);
	}

}
