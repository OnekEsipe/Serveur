package com.onek.service;

import java.util.List;

import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

public interface UserService {

	public void updateUserInfos(Utilisateur user);
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);
	public Utilisateur getUserByLogin(String login);
}
