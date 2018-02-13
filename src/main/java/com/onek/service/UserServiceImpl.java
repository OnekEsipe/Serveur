package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.UserDao;
import com.onek.model.Utilisateur;

@Service
public class UserServiceImpl implements UserService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean validPassword(int idUser, String password) {
		return userDao.validPassword(idUser, password);
	}

	@Override
	public void updateUserInfos(Utilisateur user) {
		userDao.updateUserInfos(user);	
	}

	@Override
	public Utilisateur userById(int iduser) {
		return userDao.userById(iduser);
	}

}
