package com.onek.managedbean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.PasswordService;
import com.onek.utils.Password;

@Component("resetPassword")
@Scope("session")
public class ResetPasswordBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PasswordService passwordService;

	private Utilisateur user;
	private boolean tokenIsValid;
	private String newPassword;
	private String confirmNewPassword;	
	private String token;

	public void before() {
		if (!FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("token")) {
			tokenIsValid = false;
			token = "";
			return;
		}
		token = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

		Optional<Utilisateur> optionalUser = passwordService.tokenIsValid(token);
		if (!optionalUser.isPresent()) {
			tokenIsValid = false;
			token = "";
			return;
		}
		user = optionalUser.get();
		tokenIsValid = true;
	}

	public void setTokenIsValid(boolean tokenIsValid) {
		this.tokenIsValid = tokenIsValid;
	}

	public boolean getTokenIsValid() {
		return tokenIsValid;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void reset() {
		if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
			showLog("Merci de renseigner tous les champs.");
			return;
		} else if (!newPassword.equals(confirmNewPassword)) {
			showLog("Nouveau mot de passe différent du mot de passe de confirmation.");
			return;
		}
		if (!Password.verifyPasswordRule(newPassword)) {
			showLog("Le mot de passe doit être composé d'au moins 6 caractères<br/>et comporter au moins une majuscule.");
			return;
		}
		try {
			passwordService.updatePassword(user, newPassword);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			showLog("Une erreur interne a empêché la réinitialisation du mot de passe.");
			return;
		}
		showLog("Votre mot de passe a été réinitialisé avec succès !");
	}

	private void showLog(String logInfo) {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Réinitialiser mon mot de passe", logInfo));
	}
}
