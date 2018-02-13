package com.onek.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.LoginDao;
import com.onek.model.Utilisateur;
import com.onek.utils.EncodePassword;

@Service
public class LoginServiceImpl implements LoginService, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LoginDao logindao;

	@Override
	public Utilisateur findUserByLogin(String login) {
		return logindao.findUserByLogin(login);
	}
	
	@Override
	public boolean userExist(String login) {
		return logindao.userExist(login);
	}

}
