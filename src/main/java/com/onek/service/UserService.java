package com.onek.service;

import com.onek.model.Utilisateur;

public interface UserService {

	public void updateUserInfos(Utilisateur user);
	public boolean userExistAndCorrectPassword(String login, String password);
	public Utilisateur getUserByLogin(String login);

}
