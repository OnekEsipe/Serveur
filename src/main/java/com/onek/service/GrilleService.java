package com.onek.service;

import java.util.List;

import com.onek.model.Critere;

public interface GrilleService {

	public void addCriteres(List<Critere> criteres);
	public void addCritere(Critere critere);
	public Critere getCritereById(int id);
	public void supprimerCritere(int id);

}
