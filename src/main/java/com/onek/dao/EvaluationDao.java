package com.onek.dao;

import com.onek.model.Evaluation;

public interface EvaluationDao {

	Evaluation findById(Integer id);
	void update(Evaluation evaluation);

	void deleteEvaluation(int idJury, int idCandidat);

}
