package com.onek.dao;

import java.util.List;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

public interface UserDao {
  
	public void addUser(Utilisateur user);
	public void deleteUser(int idUser) ;
	public boolean userExist(String login);
	public void updateUserInfos(Utilisateur user);
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);
	public Utilisateur findByLogin(String login);
	public List<Utilisateur> getAllUsers();
	public List<Utilisateur> getAllUsersExceptCurrentAndAnonymous(int idcurrentUser);
	public boolean mailExist(String mail);
	public Utilisateur findByMail(String mail);
	public void editUser(Utilisateur user);
	public Utilisateur findUserById(int iduser);
	public Utilisateur findByToken(String token);
}
