package com.onek.utils;

public enum DroitsUtilisateur {
	ADMINISTRATEUR("R"),
	ORGANISATEUR("O"),
	JURY("J"),
	ANONYME("A");

	private String name = "";
	
	DroitsUtilisateur(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
