package com.onek.resource;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Utilisateur;

public class JuryResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Id")
	private final Integer idUser;
	
	@JsonProperty("LastName")
	private final String nom;
	
	@JsonProperty("FirstName")
	private final String prenom;
	
	@JsonProperty("Candidates")
	private List<CandidatResource> candidats;
	
	public JuryResource(Utilisateur user) {
		idUser = user.getIduser();
		nom = user.getNom();
		prenom = user.getPrenom();
	}
	
	public void setCandidates(List<CandidatResource> candidats) {
		this.candidats = candidats;
	}

}
