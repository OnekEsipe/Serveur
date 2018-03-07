package com.onek.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.onek.model.Utilisateur;

/**
 * Interface de la classe PasswordServiceImpl. Couche service
 */
public interface PasswordService {
	
	/**
	 * Préparation et envoie du mail de réinitialisation du mot de passe
	 * @param mail Mail destination
	 * @return True si le processus s'est bien déroulé
	 */
	public boolean reset(String mail);
	
	/**
	 * Vérifie la validité du token pour la réinitialisation du mot de passe (30 minutes)
	 * @param token
	 * @return
	 */
	public Optional<Utilisateur> tokenIsValid(String token);
	
	/**
	 * Mis à jour du mot de passe
	 * @param user
	 * @param password
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void updatePassword(Utilisateur user, String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
