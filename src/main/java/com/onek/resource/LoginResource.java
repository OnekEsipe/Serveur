package com.onek.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Utilisateur;

public class LoginResource {

	@JsonProperty("Login")
	private String login;
	
	@JsonProperty("Password")
	private String password;
	
	/* empty constructor */
	public LoginResource() {
		
	}
	
	public LoginResource(Utilisateur user) {
		this.login = user.getLogin();
		this.password = user.getMotdepasse();
	}
	
	@JsonIgnore
	public String getLogin() {
		return login;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
}
