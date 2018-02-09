package com.onek.managedbean;

import java.io.Serializable;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

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
		
		message = "utilisateur ou mot de passe incorrect";
		// Code commenté en attendant l'implémentation de Utilisateur
		/*if (loginservice.userExist(login)) {
			if (motDePasse.equals(loginservice.findUserByLogin(login).getMotdepasse())) {
				if (loginservice.findUserByLogin(login).getDroits().equals("A")
						|| loginservice.findUserByLogin(login).getDroits().equals("O")) {
					message = "connexion réussie";
				}
			}
		}*/

		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "accueil.xhtml",
				"accueil.xhtml".contains("?") ? "&" : "?"));

	}

}
