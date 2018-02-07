package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;

public interface ApplicationService {
	
	public List<Integer> parser(String ids);
	public Evenement export(List<Integer> idEvents);
	
}
