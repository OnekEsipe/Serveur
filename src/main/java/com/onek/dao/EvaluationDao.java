package com.onek.dao;

import com.onek.model.Candidat;
import java.util.Date;

import com.onek.model.Evaluation;
import java.util.List;
import com.onek.model.Jury;

public interface EvaluationDao {

	Evaluation findById(Integer id);
	void update(Evaluation evaluation);
	List<Evaluation> findByIdCandidate(Integer idCandidat);
	void deleteEvaluation(int idJury, int idCandidat);
	void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury, Date date);
	public void saveEvaluation(Candidat candidat, Jury jury) ;
}
