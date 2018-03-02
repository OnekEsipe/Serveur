package com.onek.service;

import java.util.List;
import java.util.Optional;

import com.onek.resource.AccountResource;
import com.onek.resource.CodeEvenementResource;
import com.onek.resource.CreateJuryResource;
import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;
import com.onek.resource.PasswordModify;

public interface ApplicationService {
	
	public Optional<EvenementResource> export(String idEvent, String login);	
	public boolean importEvaluation(EvaluationResource evaluationResource);
	public List<AccountResource> account(String login);
	public void createJury(CreateJuryResource createJuryResource);
	public boolean subscribe(CodeEvenementResource eventCode);
	public boolean changePassword(PasswordModify passwordModify);
	
}
