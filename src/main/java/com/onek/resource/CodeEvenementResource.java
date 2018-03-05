package com.onek.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json code evenement
 */
public class CodeEvenementResource {

	@JsonProperty("Login")
	private String login;
	
	@JsonProperty("Event_code")
	private String eventCode;
	
	/* empty constructor */
	public CodeEvenementResource() {
		
	}
	
	@JsonIgnore
	public String getLogin() {
		return login;
	}
	
	@JsonIgnore
	public String getEventCode() {
		return eventCode;
	}
}
