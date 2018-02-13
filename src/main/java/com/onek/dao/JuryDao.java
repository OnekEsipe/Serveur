package com.onek.dao;

import java.util.List;

import com.onek.model.Jury;

public interface JuryDao {
	public boolean juryIsAssigned(int idUser, int idEvent);
	public List<Jury>  findJuryAndAnonymousByIdEvent(int idEvent, String login);
	public List<Jury> findAnonymousByIdEvent(int idEvent);
}
