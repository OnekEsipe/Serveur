package com.onek.managedbean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.UserService;
import com.onek.utils.Navigation;

@Component("userInfo")
public class AccountBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;
	
	private Utilisateur user;
	private int idUser = 1;
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
			String userName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			System.out.println("--------------------"+userName+"-----------------");
			user = userService.userById(idUser);
			lastEmail = user.getMail();
		}
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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
		}
		
		//On effectue la requête que s'il y a eu modification
		if(newModif) {
			userService.updateUserInfos(user);
		}
	}

	public boolean updatePassword() {
		if(lastPassword == null || newPassword == null || confirmNewPassword == null) return false;
		if(lastPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
			logPassword = "Merci de renseigner tous les champs";
			return false;
		}
		if(!userService.validPassword(idUser,lastPassword)) {
			logPassword = "Mot de passe incorrect, merci de réessayer.";
			return false;
		}
		if(!newPassword.equals(confirmNewPassword)) {
			logPassword = "Nouveau mot de passe différent du mot de passe de confirmation!.";
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
		return true;
	}
	


}
