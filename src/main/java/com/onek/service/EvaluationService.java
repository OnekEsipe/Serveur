package com.onek.service;

import java.util.List;
import java.util.Date;
import com.onek.model.Jury;
import com.onek.model.Evaluation;
import com.onek.model.Candidat;

/**
 * Interface de la classe EvaluationServiceImpl. Couche service
 */
public interface EvaluationService {

	/**
	 * @see com.onek.dao.EvaluationDao#findByIdCandidate(int)
	 */
	List<Evaluation> findByIdCandidate(int idCandidat);
	
	/**
	 * @see com.onek.dao.EvaluationDao#findByIdJury(int)
	 */
	List<Evaluation> findByIdJury(int idJury);
	
	/**
	 * @see com.onek.dao.EvaluationDao#deleteEvaluation(int, int)
	 */
	void deleteEvaluation(int idJury, int idCandidat);
	
	/**
	 * @see com.onek.dao.EvaluationDao#findById(int)
	 */
	public Evaluation findById(int id);
  
 	/**
	 * @see com.onek.dao.EvaluationDao#update(Evaluation)
	 */
	public void update(Evaluation evaluation);
  
	/**
	 * @see com.onek.dao.EvaluationDao#saveEvaluation(Candidat, Jury, Date, int)
	 */
	public void saveEvaluation(Candidat candidat, Jury jury, Date date, int idevent);
}
