package com.onek.dao;

import java.util.List;

import com.onek.model.Evaluation;

public interface EvaluationDao {
	
	public List<Evaluation> findByIdEvent(Integer id);

}
