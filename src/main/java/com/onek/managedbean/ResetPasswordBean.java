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

/**
 * ManagedBean ResetPasswordBean
 */
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

	/**
	 * Méthode appelée lors d'un GET sur la page forgotpassword.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
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

	/**
	 * Setter de la variable tokenIsValid
	 * @param tokenIsValid Validation du token
	 */
	public void setTokenIsValid(boolean tokenIsValid) {
		this.tokenIsValid = tokenIsValid;
	}

	/**
	 * Getter de la variable tokenIsValid
	 * @return tokenIsValid Validation du token
	 */
	public boolean getTokenIsValid() {
		return tokenIsValid;
	}

	/**
	 * Setter de la variable newPassword
	 * @param newPassword
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * Getter de la variable newPassword
	 * @return newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Setter de la variable confirmNewPassword
	 * @param confirmNewPassword
	 */
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	/**
	 * Getter de la variable confirmNewPassword
	 * @return confirmNewPassword
	 */
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	/**
	 * Navigation vers la page resetpassword.xhtml avec le token en paramètre.
	 * La validité du token est de 30min
	 */
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
