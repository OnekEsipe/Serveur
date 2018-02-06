package com.onek.managedbean;

import org.springframework.stereotype.Component;

@Component("login")
public class LoginBean {

	private String login;
	private String motDePasse;
	private String message;
	private Boolean checkbox;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(Boolean checkbox) {
		this.checkbox = checkbox;
	}
	public void buttonAction() {
		//to do
		//il faut vérifier login mdp avec la BD
		login=null;
	}
	
}
