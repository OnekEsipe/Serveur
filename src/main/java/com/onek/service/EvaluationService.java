package com.onek.service;

import java.util.Date;

import com.onek.model.Jury;

public interface EvaluationService {

	void deleteEvaluation(int idJury, int idCandidat);
	void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury, Date date);
	
}
