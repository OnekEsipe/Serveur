package com.onek.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.onek.model.Utilisateur;

public interface PasswordService {
	public boolean reset(String mail);
	public Optional<Utilisateur> tokenIsValid(String token);
	public void updatePassword(Utilisateur user, String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
