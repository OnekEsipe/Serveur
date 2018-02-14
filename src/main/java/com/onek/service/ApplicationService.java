package com.onek.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import com.onek.resource.EvaluationResource;
import com.onek.resource.AccountResource;
import com.onek.resource.CodeEvenementResource;
import com.onek.resource.CreateJuryResource;
import com.onek.resource.EvenementResource;

public interface ApplicationService {
	
	public Optional<EvenementResource> export(String idEvent, String login);	
	public EvaluationResource importEvaluation(EvaluationResource evaluationResource);
	public List<AccountResource> account(String login);
	public void createJury(CreateJuryResource createJuryResource) throws NoSuchAlgorithmException, UnsupportedEncodingException;
	public boolean subscribe(CodeEvenementResource eventCode);
	
}
