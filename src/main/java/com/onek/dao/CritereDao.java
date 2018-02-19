package com.onek.dao;

import java.util.List;

import com.onek.model.Critere;

public interface CritereDao {
	void addCriteres(List<Critere> criteres);
	Critere findById(Integer id);
}
