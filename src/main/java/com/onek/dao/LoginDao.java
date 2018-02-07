package com.onek.dao;

import com.onek.model.Utilisateur;

public interface LoginDao {

	public Utilisateur findUserByLogin(String login);
	public boolean userExist(String login);
}
