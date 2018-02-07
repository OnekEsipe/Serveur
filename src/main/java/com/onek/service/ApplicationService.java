package com.onek.service;

import java.util.List;

public interface ApplicationService {
	
	public List<String> parser(String filenames);
	public void export(List<String> filenames);
	
}
