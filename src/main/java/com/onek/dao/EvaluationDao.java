package com.onek.dao;

import com.onek.model.Candidat;
import java.util.Date;

import com.onek.model.Evaluation;
import java.util.List;
import com.onek.model.Jury;

public interface EvaluationDao {

	public Evaluation findById(int id);
	public void update(Evaluation evaluation);
	public List<Evaluation> findByIdCandidate(int idCandidat);
	public List<Evaluation> findByIdJury(int idJury);
	public void deleteEvaluation(int idJury, int idCandidat);
	public void saveEvaluation(Candidat candidat, Jury jury, Date date, int idevent);
}
