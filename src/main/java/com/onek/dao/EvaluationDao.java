package com.onek.dao;

import com.onek.model.Evaluation;
import com.onek.model.Jury;

public interface EvaluationDao {

	Evaluation findById(Integer id);
	void deleteEvaluation(int idJury, int idCandidat);
	void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury);
<<<<<<< HEAD
=======

>>>>>>> branch 'AttribCandidatJuryBack' of https://github.com/OnekEsipe/Serveur.git
}
