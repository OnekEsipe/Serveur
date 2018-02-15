package com.onek.dao;

import java.util.List;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

public interface UserDao {
  
	public void updateUserInfos(Utilisateur user);
	void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);
	public Utilisateur getUserByLogin(String login);
	public List<Utilisateur> getAllUsers();
	public List<Utilisateur> getAllUsersExceptDeleted();
	public void deleteUser(int idUser) ;
	public void addUser(Utilisateur user);
	public boolean mailExist(String mail);

}
