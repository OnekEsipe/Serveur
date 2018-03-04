package com.onek.managedbean;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.service.PasswordService;
import com.onek.service.UserService;
import com.onek.utils.Navigation;

@Component("forgotPassword")
public class ForgotPasswordBean {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService passwordService;

	private String mail;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void send() {
		if (!userService.mailExist(mail)) {
			showLog("L'adresse mail saisie ne correspond à aucun utilisateur.");
			return;
		}		
		if (passwordService.reset(mail)) {			
			showLog("Un mail contenant un lien pour redéfinir votre mot de passe a été envoyé.");
		} else {
			showLog("Une erreur interne a empêché la réinitialisation du mot de passe.");
		}
	}
	
	public void retour() {
		Navigation.redirect("index.xhtml");
	}
	
	private void showLog(String logInfo) {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Mot de passe oublié", logInfo));
	}

}
