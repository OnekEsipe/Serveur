package com.onek.service;

import com.onek.model.Utilisateur;

public interface UserService {

	public void updateUserInfos(Utilisateur user);
	public boolean validPassword(int idUser, String password);
	public Utilisateur userById(int iduser);
}
