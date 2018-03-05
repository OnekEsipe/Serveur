package com.onek.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json reset du mot de passe
 */
public class PasswordResetResource {
	
	@JsonProperty("Mail")
	private String mail;
	
	/* empty constructor */
	public PasswordResetResource() {
		
	}
	
	@JsonIgnore
	public String getMail() {
		return mail;
	}
}
