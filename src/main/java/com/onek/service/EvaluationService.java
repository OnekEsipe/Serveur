package com.onek.service;

import java.util.List;
import java.util.Map;

import com.onek.model.Candidat;
import com.onek.model.Jury;

public interface EvaluationService {
	public void saveEvaluation(Candidat candidat, Jury jury);
}
