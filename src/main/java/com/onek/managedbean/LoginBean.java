package com.onek.managedbean;

import java.io.Serializable;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.service.UserService;

@Component("login")
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	private String login;
	private String motDePasse;
	private String message;
	private Boolean checkbox;

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
				return;
			}
		}
	}

	
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
		if (!userService.authentification(login, motDePasse)) {
			message = "Utilisateur ou mot de passe incorrect.";
			return;
		}		
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getExternalContext().getSessionMap().put("user", login);
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "accueil.xhtml",
				"accueil.xhtml".contains("?") ? "&" : "?"));		
	}

}
