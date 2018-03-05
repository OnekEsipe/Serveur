package com.onek.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json mot de passe modifié
 */
public class PasswordModify implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty("Login")
	private String login;
	
	@JsonProperty("Old_password")
	private String oldPassword;
	
	@JsonProperty("New_password")
	private String newPassword;
	
	/* empty constructor */
	public PasswordModify() {
		
	}

	@JsonIgnore
	public String getLogin() {
		return login;
	}

	@JsonIgnore
	public String getOldPassword() {
		return oldPassword;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}	
	
}
