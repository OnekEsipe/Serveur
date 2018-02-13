package com.onek.dao;

import java.util.List;

import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

public interface UserDao {
	public void updateUserInfos(Utilisateur user);
	public boolean validPassword(int idUser, String password);
	public Utilisateur userById(int iduser);
	void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);

}
