package com.onek.resource;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Jury;

public class JuryResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Id")
	private final Integer idJury;
	
	@JsonProperty("Login")
	private final String login;
	
	@JsonProperty("LastName")
	private final String nom;
	
	@JsonProperty("FirstName")
	private final String prenom;
	
	@JsonProperty("Candidates")
	private List<CandidatResource> candidats;
	
	public JuryResource(Jury jury) {
		idJury = jury.getIdjury();
		login = jury.getUtilisateur().getLogin();
		nom = jury.getUtilisateur().getNom();
		prenom = jury.getUtilisateur().getPrenom();		
	}
	
	public void setCandidates(List<CandidatResource> candidats) {
		this.candidats = candidats;
	}

}
