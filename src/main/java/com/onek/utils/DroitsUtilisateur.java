package com.onek.utils;

/**
 * Enum du type d'utilisateur
 */
public enum DroitsUtilisateur {
	ADMINISTRATEUR("R", "Administrateur"),
	ORGANISATEUR("O", "Organisateur"),
	JURY("J", "Jury"),
	ANONYME("A", "Jury anonyme");

	private String name = "";
	private String value = "";
	
	/**
	 * @param name
	 * @param value
	 */
	DroitsUtilisateur(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
