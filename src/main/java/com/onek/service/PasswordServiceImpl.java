package com.onek.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.model.Utilisateur;
import com.onek.utils.Encode;

@Service
public class PasswordServiceImpl implements PasswordService, Serializable {
	private static final long serialVersionUID = 1L;

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
			e.printStackTrace();
			return false;
		}		
		
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
		message += "<a href=\"http://localhost:8080/serveur/resetpassword.xhtml?token=" + token + "\">" + ""
				+ "http://localhost:8080/serveur/resetpassword.xhtml?token=" + token + "</a><br/><br/>";
		message += "Cordialement,<br/>";
		message += "<strong>L'équipe ONEK</strong>";		
		return emailService.sendMail(mail, "[ONEK] Reinitialisation du mot de passe", message);		
	}
	
	@Override
	public Optional<Utilisateur> findToken(String token) {
		List<Utilisateur> users = userService.getAllUsers();
		for(Utilisateur user : users) {
			try {
				if (token.equals(generateToken(user))) {
					return Optional.of(user);
				}
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {				
				e.printStackTrace();
				return Optional.empty();
			}
		}
		return Optional.empty();
	}
	
	private String generateToken(Utilisateur user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return Encode.sha256(user.getLogin() + user.getMail());
	}

}
