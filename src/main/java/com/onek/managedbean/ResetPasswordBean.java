package com.onek.managedbean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.PasswordService;
import com.onek.utils.Navigation;
import com.onek.utils.Password;

/**
 * ManagedBean ResetPasswordBean
 */
@Component("resetPassword")
public class ResetPasswordBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PasswordService passwordService;

	private Utilisateur user;
	private boolean tokenIsValid;
	private String newPassword;
	private String confirmNewPassword;
	private String logInfo;
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
		token = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("token");
		
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
	 * Setter de la variable logInfo
	 * @param logInfo Le message a afficher
	 */
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	/**
	 * Getter de la variable logInfo
	 * @return logInfo Message d'information
	 */
	public String getLogInfo() {
		return logInfo;
	}

	/**
	 * Navigation vers la page resetpassword.xhtml avec le token en paramètre.
	 * La validité du token est de 30min
	 */
	public void reset() {
		logInfo = "Votre mot de passe a été réinitialisé avec succès !";
		if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
			logInfo = "Merci de renseigner tous les champs.";	
			Navigation.redirect("resetpassword.xhtml?token=" + token);
			return;
		}
		else if (!newPassword.equals(confirmNewPassword)) {
			logInfo = "Nouveau mot de passe différent du mot de passe de confirmation.";	
			Navigation.redirect("resetpassword.xhtml?token=" + token);
			return;
		}
		if (!Password.verifyPasswordRule(newPassword)) {
			logInfo = "Le mot de passe doit être composé d'au moins 6 caractères et comporter au moins une majuscule.";
			Navigation.redirect("resetpassword.xhtml?token=" + token);
			return;
		}
		try {
			passwordService.updatePassword(user, newPassword);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logInfo = "Une erreur interne a empêché la réinitialisation du mot de passe.";
		}		
		Navigation.redirect("resetpassword.xhtml?token=" + token);
	}
}
