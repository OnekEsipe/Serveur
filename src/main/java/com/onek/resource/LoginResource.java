package com.onek.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Utilisateur;

/**
 * Json login
 */
public class LoginResource {

	@JsonProperty("Login")
	private String login;
	
	@JsonProperty("Password")
	private String password;
	
	/* empty constructor */
	public LoginResource() {
		
	}
	
	/**
	 * Données d'un login : <br/>
	 * Login - Password
	 * @param user
	 */
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
