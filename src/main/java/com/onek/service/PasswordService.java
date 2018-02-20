package com.onek.service;

import java.util.Optional;

import com.onek.model.Utilisateur;

public interface PasswordService {
	public boolean reset(String mail);
	public Optional<Utilisateur> findToken(String token);
}
