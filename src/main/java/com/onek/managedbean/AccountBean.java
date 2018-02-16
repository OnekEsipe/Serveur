package com.onek.managedbean;

import java.io.Serializable;


import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.onek.model.Utilisateur;
import com.onek.service.UserService;

@Component("userInfo")
public class AccountBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	private Utilisateur user;
	private String login;
	private String lastEmail;

	private String lastPassword;
	private String newPassword;
	private String confirmNewPassword;
	private String newEmail;

	//Log
	private String logPassword;
	private String logEmail;

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			String name = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			user = userService.getUserByLogin(name);
			this.lastEmail = user.getMail();
			this.login = name;
			emptyForm();
		}
	}

	private void emptyForm() {
		setLastPassword("");
		setNewPassword("");
		setConfirmNewPassword("");
		setLastEmail("");
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

	public void modify() {
		boolean newModif = false;
		if(updatePassword()) {
			user.setMotdepasse(newPassword);
			newModif = true;
		}
		if(updateEmail()) {
			user.setMail(newEmail);
			newModif = true;
			newEmail="";
		}

		//On effectue la requête que s'il y a eu modification
		if(newModif) {
			userService.updateUserInfos(user);
		}
	}

	public boolean updatePassword() {
		if(lastPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
			logPassword = "Merci de renseigner tous les champs";
			return false;
		}
		if(!newPassword.equals(confirmNewPassword)) {
			logPassword = "Nouveau mot de passe différent du mot de passe de confirmation!.";
			return false;
		}
		
		if(!user.getMotdepasse().equals(lastPassword)) {
			logPassword = "Mot de passe incorrect, merci de réessayer.";
			return false;
		}
		 
		logPassword = "Modification effectuée avec succès!";
		return true;
	}

	public boolean updateEmail() {
		if(newEmail == null) return false;
		if(newEmail.isEmpty()) {
			logEmail = "Merci de saisir votre adresse mail";
			return false;
		}
		logEmail = "Modification effectuée avec succès!";
		lastEmail = newEmail;
		return true;
	}



}
