package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.UserDao;

@Service
public class PasswordServiceImpl implements PasswordService, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserDao userDao;
	
	//@Autowired
	private EmailService emailService;
	
	@Override
	public boolean reset(String mail) {
		if (!userDao.mailExist(mail)) {
			return false;
		}
		emailService.sendMail("onek2018esipe@gmail.com", "onek2018esipe@gmail.com", "Réinitialisation du mot de passe", "toto");
		return false;
	}
	
	
}
