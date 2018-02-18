package com.onek.dao;

import java.util.Date;

import com.onek.model.Evaluation;
import com.onek.model.Jury;

public interface EvaluationDao {

	Evaluation findById(Integer id);
	void deleteEvaluation(int idJury, int idCandidat);
	void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury, Date date);
	void update(Evaluation evaluation);
}
