package com.onek.service;

import java.util.List;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

public interface UserService {

	public void updateUserInfos(Utilisateur user);
	public boolean userExistAndCorrectPassword(String login, String password);
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);
	public Utilisateur findByLogin(String login);
	public boolean userExist(String login);
	public List<Utilisateur> getAllUsers();
	public List<Utilisateur> getAllUsersExceptCurrentAndAnonymous(int idcurrentUser);
	public List<Utilisateur> findAllJurys();
	public void deleteUser(int idUser);
	public void addUser(Utilisateur user);
	public boolean authentification(String login, String password);
	public boolean mailExist(String mail);
	public Utilisateur findByMail(String mail);
	public void editUser(Utilisateur user);
	public Utilisateur findUserById(int iduser);
}
