package com.onek.dao;

import org.hibernate.Session;

import com.onek.model.Candidat;
import com.onek.model.Evaluation;
import com.onek.model.Jury;

public interface EvaluationDao {

	Evaluation findById(Integer id);
	public void saveEvaluation(Candidat candidat, Jury jury) ;
}
