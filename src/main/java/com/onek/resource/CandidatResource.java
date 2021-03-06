package com.onek.resource;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Candidat;

/**
 * Json candidat
 */
public class CandidatResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Id")
	private final Integer idCandidat;
	
	@JsonProperty("LastName")
	private final String nom;
	
	@JsonProperty("FirstName")
	private final String prenom;		
	
	/**
	 * Données d'un candidat :<br/>
	 * Id - LastName - FirstName
	 * @param candidat Candidat
	 */
	public CandidatResource(Candidat candidat) {
		Objects.requireNonNull(candidat);
		idCandidat = candidat.getIdcandidat();
		nom = candidat.getNom();
		prenom = candidat.getPrenom();
	}

}
