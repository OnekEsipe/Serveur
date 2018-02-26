
package com.onek.service;

import java.util.List;
import java.util.Date;
import com.onek.model.Jury;
import com.onek.model.Evaluation;
import com.onek.model.Candidat;

public interface EvaluationService {

	List<Evaluation> findByIdCandidate(Integer idCandidat);
	List<Evaluation> findByIdJury(Integer idJury);
	void deleteEvaluation(int idJury, int idCandidat);
	public void saveEvaluation(Candidat candidat, Jury jury, Date date, int idevent);
}
