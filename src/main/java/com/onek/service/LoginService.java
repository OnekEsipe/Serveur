package com.onek.service;

import com.onek.model.Utilisateur;

public interface LoginService {
	public Utilisateur findUserByLogin(String login);
	public boolean userExist(String login) ;
}
