package com.onek.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.UserDao;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;
import com.onek.utils.DroitsUtilisateur;
import com.onek.utils.Encode;

/**
 * Service UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private EmailService emailService;

	@Override
	public Utilisateur findByLogin(String login) {

		return userDao.findByLogin(Objects.requireNonNull(login));
	}

	@Override
	public boolean userExist(String login) {
		return userDao.userExist(login);
	}

	@Override
	public void updateUserInfos(Utilisateur user) {
		userDao.updateUserInfos(user);
	}

	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event) {
		userDao.addJurysAnonymes(utilisateurs, event);
	}

	@Override
	public List<Utilisateur> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void deleteUser(int idUser) {
		userDao.deleteUser(idUser);
	}

	@Override
	public void addUser(Utilisateur user) {
		if (userExist(user.getLogin())) {
			throw new IllegalStateException("Le login est déjà utilisé.");
		}
		if (mailExist(user.getMail())) {
			throw new IllegalStateException("L'adresse mail est déjà utilisée.");
		}
		user.setMail(user.getMail().toLowerCase());
		userDao.addUser(user);
	}

	@Override
	public List<Utilisateur> getAllUsersExceptCurrentAndAnonymous(int idcurrentUser) {
		return userDao.getAllUsersExceptCurrentAndAnonymous(idcurrentUser);
	}

	@Override
	public boolean userExistAndCorrectPassword(String login, String password) {
		Objects.requireNonNull(login);
		Objects.requireNonNull(password);
		if (!userExist(login)) {
			return false;
		}
		if (password.isEmpty()) {
			return false;
		}
		Utilisateur user = findByLogin(login);
		if (user.getIsdeleted()) {
			return false;
		}
		if (user.getDroits().equals(DroitsUtilisateur.ANONYME.toString())) {
			String hash;
			try {
				hash = Encode.sha1(user.getMotdepasse());
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				logger.error(this.getClass().getName(), e);
				throw new IllegalStateException();
			}
			return hash.equals(password);
		}
		return user.getMotdepasse().equals(password);
	}

	@Override
	public boolean authentification(String login, String password) {
		String hash;
		try {
			hash = Encode.sha1(password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error(this.getClass().getName(), e);
			return false;
		}
		if (!userExistAndCorrectPassword(login, hash)) {
			return false;
		}
		Utilisateur user = findByLogin(login);
		if (user.getDroits().equals(DroitsUtilisateur.JURY.toString())
				|| user.getDroits().equals(DroitsUtilisateur.ANONYME.toString())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean mailExist(String mail) {
		return userDao.mailExist(mail.toLowerCase());
	}

	@Override
	public Utilisateur findByMail(String mail) {
		return userDao.findByMail(mail);
	}

	@Override
	public Utilisateur findUserById(int iduser) {
		return userDao.findUserById(iduser);
	}

	@Override
	public Utilisateur findByToken(String token) {
		return userDao.findByToken(token);
	}

	@Override
	public List<Utilisateur> getAllUsersExceptDeleted() {
		return userDao.getAllUsersExceptDeletedansAno();
	}

	@Override
	public boolean sendInscriptionMail(Utilisateur user, String password) {
		String url = emailService.getMailBean().getUrl();
		String message;

		if (user.getPrenom() == null || user.getPrenom().isEmpty()) {
			message = "Bonjour,<br/><br/>";
		} else {
			message = "Bonjour " + user.getPrenom() + ",<br/><br/>";
		}
		message += "Vous recevez ce message car un administrateur vous a inscrit à <strong>l'application ONEK'</strong>";
		message += " en tant qu'<strong>" + user.getNomDroits().toLowerCase() + "</strong>.<br/><br/>";
		message += "Vous trouverez vos informations de connexion ci-dessous :<br/>";
		message += "Login : <strong>" + user.getLogin() + "</strong><br/>";
		message += "Mot de passe : <strong>" + password + "</strong><br/><br/>";
		message += "Vous pouvez accéder à l'application via le lien suivant :<br/>";
		message += "<a href=\"" + url + "index.xhtml\">" + url + "index.xhtml</a><br/><br/>";
		message += "Nous vous conseillons de modifier votre mot de passe dès votre première connexion.<br/><br/>";		
		message += "Cordialement,<br/>";
		message += "<strong>L'équipe ONEK'</strong>";		

		return emailService.sendMail(user.getMail(), "[ONEK'] Inscription", message);
	}
	@Override
	public List<Utilisateur> getDeletenotAno(){
		return userDao.getDeletenotAno();
	}
}
