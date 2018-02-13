package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.UserDao;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

@Service
public class UserServiceImpl implements UserService, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserDao userDao;

	@Override
	public void updateUserInfos(Utilisateur user) {
		userDao.updateUserInfos(user);
	}

	@Override
	public Utilisateur getUserByLogin(String login) {
		return userDao.getUserByLogin(login);
	}

	@Override
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event) {
		userDao.addJurysAnonymes(utilisateurs, event);
	}

}
