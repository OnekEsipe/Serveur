package com.onek.dao;

import java.util.List;

import com.onek.model.Utilisateur;

public interface UserDao {
	public void updateUserInfos(Utilisateur user);
	public Utilisateur getUserByLogin(String login);
	public List<Utilisateur> getAllUsers();

}
