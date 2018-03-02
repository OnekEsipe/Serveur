package com.onek.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Descripteur;

/**
 * Json descripteur
 */
public class DescripteurResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Level")
	private String niveau;

	@JsonProperty("Text")
	private String texte;
	
	/* empty constructor */
	public DescripteurResource() {
		
	}
	
	/**
	 * Données d'un descripteur : <br/>
	 * Level - Text
	 * @param descripteur
	 */
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
