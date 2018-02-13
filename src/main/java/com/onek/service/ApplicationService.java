package com.onek.service;

import java.util.List;
import java.util.Optional;

import com.onek.resource.AccountResource;
import com.onek.resource.EvenementResource;

public interface ApplicationService {
	
	public Optional<EvenementResource> export(String idEvent, String login);
	public List<AccountResource> account(String login);
}
