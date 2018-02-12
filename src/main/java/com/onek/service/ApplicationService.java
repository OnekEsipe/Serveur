package com.onek.service;

import java.util.Optional;

import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;

public interface ApplicationService {
	
	public Optional<EvenementResource> export(String idEvent, String login);	
	public EvaluationResource importEvaluation(EvaluationResource evaluationResource);

}
