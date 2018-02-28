package com.onek.managedbean;

import java.io.Serializable;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.UserService;
import com.onek.utils.Encode;
import com.onek.utils.Navigation;
import com.onek.utils.Password;

@Component("userInfo")
public class AccountBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(AccountBean.class);

	@Autowired
	private UserService userService;

	private Utilisateur user;
	private String login;
	private String lastEmail;

	private String lastPassword;
	private String newPassword;
	private String confirmNewPassword;
	private String newEmail;

	// Log
	private String logPassword;
	private String logEmail;

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				Navigation.redirect("index.xhtml");
				return;
			}
			String name = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			user = userService.findByLogin(name);

			this.lastEmail = user.getMail();
			this.login = name;
			emptyForm();
		}
	}

	private void emptyForm() {
		setLastPassword("");
		setNewPassword("");
		setConfirmNewPassword("");
		setNewEmail("");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getLogPassword() {
		return logPassword;
	}

	public void setLogPassword(String logPassword) {
		this.logPassword = logPassword;
	}

	public String getLogEmail() {
		return logEmail;
	}

	public void setLogEmail(String logEmail) {
		this.logEmail = logEmail;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public String getLastEmail() {
		return lastEmail;
	}

	public void setLastEmail(String lastEmail) {
		this.lastEmail = lastEmail;
	}

	public void modifyPassword() {
		logEmail = "";
		if (lastPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
			showMessagePassword("Merci de renseigner tous les champs.");
			return;
		}
		if (!newPassword.equals(confirmNewPassword)) {
			showMessagePassword("Nouveau mot de passe différent du mot de passe de confirmation.");
			return;
		}
		try {
			String hash = Encode.sha1(lastPassword);
			if (!user.getMotdepasse().equals(hash)) {
				showMessagePassword("Mot de passe incorrect, merci de réessayer.");
				return;
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			showMessagePassword("Une erreur est survenue.");
			logger.error(this.getClass().getName(), e);
			return;
		}
		if (!Password.verifyPasswordRule(newPassword)) {
			showMessagePassword("Le mot de passe doit être composé d'au moins 6 caractères et comporter au moins une majuscule.");
			return;
		}		
		try {
			String hash = Encode.sha1(newPassword);
			user.setMotdepasse(hash);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error(this.getClass().getName(), e);
			showMessagePassword("Une erreur est survenue.");
			return;
		}
		userService.updateUserInfos(user);
		showMessagePassword("Modification effectuée avec succès !");
	}

	public void modifyMail() {
		logPassword = "";
		if (newEmail == null)
			return;
		if (newEmail.isEmpty()) {			
			newEmail = "";
			showMessageMail("Merci de saisir votre adresse mail.");
			return;
		}
		if (userService.mailExist(newEmail)) {			
			newEmail = "";
			showMessageMail("L'adresse mail est déjà utilisée.");
			return;
		}
		lastEmail = newEmail;
		user.setMail(newEmail);
		newEmail = "";
		userService.updateUserInfos(user);
		showMessageMail("Modification effectuée avec succès !");
	}
	
	public void showMessagePassword(String logPassword) {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification du mot de passe", logPassword));
	}
	
	public void showMessageMail(String logMail) {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification de l'adresse mail", logMail));
	}
}
