package com.onek.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.UserDao;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;
import com.onek.utils.DroitsUtilisateur;
import com.onek.utils.Encode;

@Service
public class UserServiceImpl implements UserService, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public Utilisateur findByLogin(String login) {
		return userDao.findByLogin(login);
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
	public List<Utilisateur> findAllJurys() {
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
		userDao.addUser(user);
	}

	@Override
	public List<Utilisateur> getAllUsersExceptDeleted() {
		return userDao.getAllUsersExceptDeleted();
	}
	
	@Override
	public boolean userExistAndCorrectPassword(String login, String password) {
		if (!userExist(login)) {
			return false;
		}
		if (password == null || password.isEmpty()) {
			return false;
		}
		Utilisateur user = findByLogin(login);
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
		return userDao.mailExist(mail);
	}
	
	@Override
	public Utilisateur findByMail(String mail) {
		return userDao.findByMail(mail);
	}
}
