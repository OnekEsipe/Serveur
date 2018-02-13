package com.onek.service;

import java.util.List;

import com.onek.model.Utilisateur;

public interface UserService {

	public void updateUserInfos(Utilisateur user);
	public Utilisateur getUserByLogin(String login);
	public List<Utilisateur> getAllUsers();
	public List<Utilisateur> getAllUsersExceptDeleted();
	public void deleteUser(int id);
	public void addUser(Utilisateur user);
}
