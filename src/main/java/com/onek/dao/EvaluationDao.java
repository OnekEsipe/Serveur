package com.onek.dao;

import java.util.List;

import com.onek.model.Evaluation;

public interface EvaluationDao {

	Evaluation findById(Integer id);
	
	List<Evaluation> findByIdCandidate(Integer idCandidat);

}
