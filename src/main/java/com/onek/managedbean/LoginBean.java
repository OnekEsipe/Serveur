package com.onek.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.onek.service.UserService;
import com.onek.utils.Navigation;

@Configuration
@Component("login")
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(LoginBean.class);	
	
	@Autowired
	private UserService userService;

	private String login;
	private String motDePasse;
	private String message;
	private String version;

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
				return;
			}
			this.login = "";
			this.motDePasse = "";
			this.message = "";
			readVersion();
		}		
	}

	public UserService getUserService() {
		return userService;
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
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void buttonAction() {
		if (!userService.authentification(login, motDePasse)) {
			message = "Utilisateur ou mot de passe incorrect";
			return;
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getExternalContext().getSessionMap().put("user", login);
		Navigation.redirect("accueil.xhtml");
	}
	
	private void readVersion() {
		if (version != null) {
			return;
		}
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException | NullPointerException e) {
			logger.error("Error to read project.properties", e);
			return;
		}
		version = "v" + properties.getProperty("onek.server.version");		
	}

}
