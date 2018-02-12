package com.onek.dao;

import com.onek.model.Utilisateur;

public interface UserDao {
	public void updateUserInfos(Utilisateur user);
	public boolean validPassword(int idUser, String password);
	public Utilisateur userById(int iduser);

}
