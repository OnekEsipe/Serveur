package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

public interface UserService {

	public void updateUserInfos(Utilisateur user);
	public boolean validPassword(int idUser, String password);
	public Utilisateur userById(int iduser);
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);
}
