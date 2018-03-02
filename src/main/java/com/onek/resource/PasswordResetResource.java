package com.onek.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
