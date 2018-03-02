package com.onek.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Critere;
import com.onek.model.Descripteur;

public class CritereResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Id")
	private final Integer idCritere;
	
	@JsonProperty("Category")
	private final String categorie;
	
	@JsonProperty("Text")
	private final String texte;
	
	@JsonProperty("Descriptor")
	private final List<DescripteurResource> descripteurs = new ArrayList<>();

	public CritereResource(Critere critere) {
		idCritere = critere.getIdcritere();
		categorie = critere.getCategorie();
		texte = critere.getTexte();
		for(Descripteur descripteur : critere.getDescripteurs()) {
			descripteurs.add(new DescripteurResource(descripteur));
		}
	}
	
}
