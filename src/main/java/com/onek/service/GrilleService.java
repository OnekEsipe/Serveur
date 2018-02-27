package com.onek.service;

import java.util.List;

import com.onek.model.Critere;
import com.onek.model.Descripteur;

public interface GrilleService {

	public void addCriteres(List<Critere> criteres);
	public void addCritere(Critere critere);
	public Critere getCritereById(int id);
	public void supprimerCritere(int id);
	public void updateCritere(Critere critere);
	
	public void supprimerDescripteur(Descripteur descripteur);
}
