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

/**
 * ManagedBean LoginBean
 */
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
	private Boolean checkbox;
	private String version;

	/**
	 * Méthode appelée lors d'un GET sur la page index.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
				return;
			}
			readVersion();
		}		
	}

	/**
	 * Getter de la variable login
	 * @return login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Setter de la variable login
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter de la variable motDePasse
	 * @return motDePasse
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * Setter de la variable motDePasse
	 * @param motDePasse
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * Getter de la variable message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter de la variable message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter de la variable checkbox
	 * @return checkbox
	 */
	public Boolean getCheckbox() {
		return checkbox;
	}

	/**
	 * Setter de la variable checkbox
	 * @param checkbox
	 */
	public void setCheckbox(Boolean checkbox) {
		this.checkbox = checkbox;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Réalise le processus d'authentification
	 * @see com.onek.service.UserService#authentification(String, String)
	 */
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
