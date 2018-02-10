package com.onek.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Descripteur;

public class DescripteurResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Level")
	private String niveau;

	@JsonProperty("Text")
	private String texte;
	
	/* empty constructor */
	public DescripteurResource() {
		
	}
	
	public DescripteurResource(Descripteur descripteur) {		
		niveau = descripteur.getNiveau();	
		texte = descripteur.getTexte();
	}	
	
	public String getNiveau() {
		return niveau;
	}	

	public String getTexte() {
		return texte;
	}	
	
}
