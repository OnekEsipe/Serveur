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
import com.onek.service.UserService;
import com.onek.utils.Encode;
import com.onek.utils.Navigation;
import com.onek.utils.Password;

@Component("resetPassword")
public class ResetPasswordBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UserService userService;

	private Utilisateur user;
	private boolean tokenIsValid;
	private String newPassword;
	private String confirmNewPassword;
	private String logInfo;
	private String token;

	public void before() {
		if (!FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("token")) {
			tokenIsValid = false;
			token = "";
			return;
		}
		token = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("token");
		Optional<Utilisateur> optionalUser = passwordService.findToken(token);
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

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void reset() {
		logInfo = "Votre mot de passe a été réinitialisé avec succès !";
		if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
			logInfo = "Merci de renseigner tous les champs.";	
			return;
		}
		else if (!newPassword.equals(confirmNewPassword)) {
			logInfo = "Nouveau mot de passe différent du mot de passe de confirmation.";	
			return;
		}
		if (!Password.verifyPasswordRule(newPassword)) {
			logInfo = "Le mot de passe doit être composé d'au moins 6 caractères et comporter au moins une majuscule.";
			return;
		}
		try {
			user.setMotdepasse(Encode.sha1(newPassword));
			userService.updateUserInfos(user);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logInfo = "Une erreur interne a empêché la réinitilisation du mot de passe.";
		}		
		Navigation.redirect("resetpassword.xhtml?token=" + token);
	}
}
