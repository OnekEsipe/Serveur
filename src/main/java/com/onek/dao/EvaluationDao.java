package com.onek.dao;

import com.onek.model.Evaluation;
import java.util.List;

public interface EvaluationDao {

	Evaluation findById(Integer id);
	void update(Evaluation evaluation);
	List<Evaluation> findByIdCandidate(Integer idCandidat);


}
