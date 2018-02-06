package com.onek.managedbean;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.service.LoginService;

@Component("login")
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private LoginService loginservice;

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
		// to do
		// il faut vérifier login mdp avec la BD
		message = "utilisateur ou mot de passe incorrect";
		if (loginservice.userExist(login)) {
			if (motDePasse.equals(loginservice.findUserByLogin(login).getMotdepasse())) {
				if (loginservice.findUserByLogin(login).getDroits().equals("A")
						|| loginservice.findUserByLogin(login).getDroits().equals("O")) {
					message = "connexion réussie";
				}
			}
		}

	}

}
