package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

public interface UserService {

	public void updateUserInfos(Utilisateur user);
	public boolean userExistAndCorrectPassword(String login, String password);
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);
	public Utilisateur getUserByLogin(String login);

	public List<Utilisateur> getAllUsers();
	public void deleteUser(Utilisateur user);
	public void addUser(Utilisateur user);
}
