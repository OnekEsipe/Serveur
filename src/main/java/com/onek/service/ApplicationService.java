package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;

public interface ApplicationService {
	
	public List<String> parser(String filenames);
	public Evenement export(List<String> filenames);
	
}
