package com.onek.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.LoginDao;
import com.onek.dao.UserDao;
import com.onek.model.Utilisateur;
import com.onek.utils.EncodePassword;

@Service
public class UserServiceImpl implements UserService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginDao loginDao;
	
	@Override
	public void updateUserInfos(Utilisateur user) {
		userDao.updateUserInfos(user);	
	}

	@Override
	public Utilisateur userById(int iduser) {
		return userDao.userById(iduser);
	}
	
	@Override
	public boolean userExistAndCorrectPassword(String login, String password) {	
		if (!loginDao.userExist(login)) {
			return false;
		}
		Utilisateur user = loginDao.findUserByLogin(login);
		if (user.getDroits().equals("A")) {
			return user.getMotdepasse().equals(password);
		}
		String hash;
		try {
			hash = EncodePassword.sha1(password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IllegalStateException();			
		}
		return user.getMotdepasse().equals(hash);
	}

}
