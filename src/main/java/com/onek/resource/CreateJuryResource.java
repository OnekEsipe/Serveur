package com.onek.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateJuryResource {

	@JsonProperty("Lastname")
	private String lastname;
	
	@JsonProperty("Firstname")
	private String firstname;
	
	@JsonProperty("Login")
	private String login;
	
	@JsonProperty("Password")
	private String password;
	
	@JsonProperty("Mail")
	private String mail;
	
	/* empty constructor */
	public CreateJuryResource() {
		
	}
	
	@JsonIgnore
	public String getLastname() {
		return lastname;
	}
	
	@JsonIgnore
	public String getFirstname() {
		return firstname;
	}
	
	@JsonIgnore
	public String getLogin() {
		return login;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}	

	@JsonIgnore
	public String getMail() {
		return mail;
	}
	
}
