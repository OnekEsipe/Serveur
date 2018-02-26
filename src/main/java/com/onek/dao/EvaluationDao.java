package com.onek.dao;

import com.onek.model.Candidat;
import java.util.Date;

import com.onek.model.Evaluation;
import java.util.List;
import com.onek.model.Jury;

public interface EvaluationDao {

	Evaluation findById(int id);
	void update(Evaluation evaluation);
	List<Evaluation> findByIdCandidate(int idCandidat);
	List<Evaluation> findByIdJury(int idJury);
	void deleteEvaluation(int idJury, int idCandidat);
	public void saveEvaluation(Candidat candidat, Jury jury, Date date, int idevent);
}
