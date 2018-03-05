package com.onek.managedbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.service.PasswordService;
import com.onek.service.UserService;
import com.onek.utils.Navigation;

/**
 * ManagedBean ForgotPasswordBean
 */
@Component("forgotPassword")
public class ForgotPasswordBean {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService passwordService;

	private String logInfo;
	private String mail;

	/**
	 * Getter de la variable logInfo
	 * @return logInfo Message d'information
	 */
	public String getLogInfo() {
		return logInfo;
	}

	/**
	 * Setter de la variable logInfo
	 * @param logInfo Le message a afficher
	 */
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	/**
	 * Getter de la variable mail
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * Setter de la variable mail
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Envoi le mail de réinitialisation de mot de passe
	 */
	public void send() {
		if (!userService.mailExist(mail)) {
			logInfo = "L'adresse mail saisie ne correspond à aucun utilisateur.";
			return;
		}		
		if (passwordService.reset(mail)) {			
			logInfo = "Un mail contenant un lien pour redéfinir votre mot de passe a été envoyé.";
		} else {
			logInfo = "Une erreur interne a empêché la réinitialisation du mot de passe.";
		}
	}
	
	/**
	 * Navigation vers index.xhtml
	 */
	public void retour() {
		Navigation.redirect("index.xhtml");
	}

}
