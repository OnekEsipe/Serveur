package com.onek.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Candidat;

public class CandidatResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Id")
	private final Integer idCandidat;
	
	@JsonProperty("LastName")
	private final String nom;
	
	@JsonProperty("FirstName")
	private final String prenom;		
	
	public CandidatResource(Candidat candidat) {
		idCandidat = candidat.getIdcandidat();
		nom = candidat.getNom();
		prenom = candidat.getPrenom();
	}

}
