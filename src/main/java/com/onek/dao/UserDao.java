package com.onek.dao;

import com.onek.model.Utilisateur;

public interface UserDao {
	public void updateUserInfos(Utilisateur user);
	public Utilisateur getUserByLogin(String login);
}
