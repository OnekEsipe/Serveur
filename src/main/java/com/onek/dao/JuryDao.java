package com.onek.dao;

import java.util.List;

import com.onek.model.Jury;
import com.onek.model.Utilisateur;

public interface JuryDao {
	public boolean juryIsAssigned(int idUser, int idEvent);
	public List<Jury>  findJuryAndAnonymousByIdEvent(int idEvent, String login);
	public List<Jury> findAnonymousByIdEvent(int idEvent);
	public List<Jury> findByUser(Utilisateur user);
}
