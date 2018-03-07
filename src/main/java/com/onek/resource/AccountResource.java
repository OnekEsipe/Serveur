package com.onek.resource;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Utilisateur;

/**
 * Json account
 */
public class AccountResource {

	@JsonProperty("Id")
	private Integer idUser;
	
	@JsonProperty("Login")
	private String login;
	
	@JsonProperty("Password")
	private String password;
	
	@JsonProperty("Events_id")
	private final List<Integer> idEvents;
	
	/**
	 * Données d'un utilisateur avec la liste des événements auquel il est lié :<br/>
	 * idUser - Login - Password - Events_id
	 * @param user Utilisateur
	 * @param idEvents Id des événements
	 */
	public AccountResource(Utilisateur user, List<Integer> idEvents) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(idEvents);
		this.idUser = user.getIduser();
		this.login = user.getLogin();
		this.password = user.getMotdepasse();
		this.idEvents = idEvents;		
	}
}
