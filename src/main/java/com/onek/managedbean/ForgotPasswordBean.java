package com.onek.managedbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.service.PasswordService;
import com.onek.service.UserService;

@Component("forgotPassword")
public class ForgotPasswordBean {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService passwordService;

	private String logInfo;
	private String mail;

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void send() {
		if (userService.mailExist(mail)) {
			passwordService.reset(mail);
			logInfo = "Un mail contenant un lien pour redéfinir votre mot de passe à été envoyé.";
		} else {
			logInfo = "L'adresse mail saisie ne correspond à aucun utilisateur.";
		}
	}

}
