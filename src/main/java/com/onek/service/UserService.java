package com.onek.service;

import com.onek.model.Utilisateur;

public interface UserService {

	public void updateUserInfos(Utilisateur user);
	public Utilisateur userById(int iduser);
	public boolean userExistAndCorrectPassword(String login, String password);
}
