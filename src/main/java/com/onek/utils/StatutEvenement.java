package com.onek.utils;

public enum StatutEvenement {
	BROUILLON("Brouillon"),
	OUVERT("Ouvert"),
	FERME("Ferme");
	
	private String name = "";
	
	StatutEvenement(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
