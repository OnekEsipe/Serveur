
package com.onek.service;

import java.util.List;
import com.onek.model.Jury;
import com.onek.model.Evaluation;
import com.onek.model.Candidat;

public interface EvaluationService {

List<Evaluation> findByIdCandidate(Integer idCandidat);
  void deleteEvaluation(int idJury, int idCandidat);
	void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury);
	public void saveEvaluation(Candidat candidat, Jury jury);
}
