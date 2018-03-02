package com.onek.service;

import java.util.List;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

/**
 * Interface de la classe UserServiceImpl. Couche service
 */
public interface UserService {

	/**
	 * @see com.onek.dao.UserDao#updateUserInfos(Utilisateur)
	 */
	public void updateUserInfos(Utilisateur user);
	
	/**
	 * Vérifie la concordance des mots de passe
	 * @param login
	 * @param password
	 * @return True si ok
	 */
	public boolean userExistAndCorrectPassword(String login, String password);
	
	/**
	 * @see com.onek.dao.UserDao#addJurysAnonymes(java.util.List, Evenement)
	 */
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);
	
	/**
	 * @see com.onek.dao.UserDao#findByLogin(String)
	 */
	public Utilisateur findByLogin(String login);
	
	/**
	 * @see com.onek.dao.UserDao#userExist(String)
	 */
	public boolean userExist(String login);
	
	/**
	 * @see com.onek.dao.UserDao#getAllUsers()
	 */
	public List<Utilisateur> getAllUsers();
	
	/**
	 * @see com.onek.dao.UserDao#getAllUsersExceptCurrentAndAnonymous(int)
	 */
	public List<Utilisateur> getAllUsersExceptCurrentAndAnonymous(int idcurrentUser);
	
	/**
	 * @see com.onek.dao.UserDao#deleteUser(int)
	 */
	public void deleteUser(int idUser);
	
	/**
	 * @see com.onek.dao.UserDao#addUser(Utilisateur)
	 */
	public void addUser(Utilisateur user);
	
	/**
	 * Réalise le processus d'authentification
	 * @param login
	 * @param password
	 * @return True si OK
	 */
	public boolean authentification(String login, String password);
	
	/**
	 * @see com.onek.dao.UserDao#mailExist(String)
	 */
	public boolean mailExist(String mail);
	
	/**
	 * @see com.onek.dao.UserDao#findByMail(String)
	 */
	public Utilisateur findByMail(String mail);
	
	/**
	 * @see com.onek.dao.UserDao#findUserById(int)
	 */
	public Utilisateur findUserById(int iduser);
	
	/**
	 * @see com.onek.dao.UserDao#findByToken(String)
	 */
	public Utilisateur findByToken(String token);
	
	/**
	 * @see com.onek.dao.UserDao#getAllUsersExceptDeleted()
	 */
	public List<Utilisateur> getAllUsersExceptDeleted();
}
