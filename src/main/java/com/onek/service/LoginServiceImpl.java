package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.LoginDao;
import com.onek.dao.UserDao;
import com.onek.model.Utilisateur;
import com.onek.utils.DroitsUtilisateur;

@Service
public class LoginServiceImpl implements LoginService, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private LoginDao logindao;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	@Override
	public Utilisateur findUserByLogin(String login) {
		return logindao.findUserByLogin(login);
	}

	@Override
	public boolean userExist(String login) {
		return logindao.userExist(login);
	}

	@Override
	public boolean authentification(String login, String password) {
		if (!userService.userExistAndCorrectPassword(login, password)) {
			return false;
		}
		Utilisateur user = userDao.getUserByLogin(login);
		if (user.getDroits().equals(DroitsUtilisateur.JURY.toString())
				|| user.getDroits().equals(DroitsUtilisateur.ANONYME.toString())) {
			return false;
		}
		return true;
	}

}
