package com.onek.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.model.Utilisateur;
import com.onek.utils.Encode;

/**
 * Service PasswordServiceImpl
 */
@Service
public class PasswordServiceImpl implements PasswordService, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(PasswordServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Override
	public boolean reset(String mail) {
		if (!userService.mailExist(mail)) {
			return false;
		}		
		Utilisateur user = userService.findByMail(mail);		
		String token;
		try {
			token = generateToken(user);
		} catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error(this.getClass().getName(), e);
			return false;
		}
		user.setToken(token);
		user.setDatetoken(new Date());
		userService.updateUserInfos(user);
		
		String url = emailService.getMailBean().getUrl();
		String message;		
		if (user.getPrenom() == null || user.getPrenom().isEmpty()) {
			message = "Bonjour,<br/><br/>";
		}
		else {
			message = "Bonjour " + user.getPrenom() + ",<br/><br/>";
		}
		message += "Vous recevez ce message parce qu'une réinitialisation du mot de passe de votre compte utilisateur a "
				+ "été demandée pour <strong>l'application ONEK</strong>.<br/><br/>";
		message += "ATTENTION<br/>";
		message += "Si vous n'avez pas demandé une réinitialisation du mot de passe, IGNOREZ et EFFACEZ cet email. "
				+ "Continuez uniquement si vous souhaitez que votre mot de passe soit réinitialisé.<br/><br/>";
		message += "Cliquez ou recopiez simplement le lien et complétez le reste du formulaire :<br/>";
		message += "<a href=\"" + url +"?token=" + token + "\">" + ""
				+  url + "?token=" + token + "</a><br/><br/>";
		message += "Cordialement,<br/>";
		message += "<strong>L'équipe ONEK</strong>";		
		return emailService.sendMail(mail, "[ONEK] Reinitialisation du mot de passe", message);		
	}
	
	@Override
	public Optional<Utilisateur> tokenIsValid(String token) {
		if (token == null || token.isEmpty()) {
			return Optional.empty();
		}
		Utilisateur user = userService.findByToken(token);
		if (user == null) {
			return Optional.empty();
		}		
		Long dateValidToken = user.getDatetoken().getTime() + 1_600_000; // 30 minutes
		Long currentDate = new Date().getTime();	
		if (dateValidToken < currentDate) {
			return Optional.empty();
		}
		return Optional.of(user);		
	}
	
	@Override
	public void updatePassword(Utilisateur user, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		user.setMotdepasse(Encode.sha1(password));
		Date date = new Date();
		date.setTime(new Date().getTime() - 1_585_000); // token valid while 15 seconds
		user.setDatetoken(date);
		userService.updateUserInfos(user);		
	}
	
	private String generateToken(Utilisateur user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return Encode.sha256(user.getLogin() + user.getMail() + new Date().toString());
	}

}
