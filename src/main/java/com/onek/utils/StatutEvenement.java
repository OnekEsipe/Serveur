package com.onek.utils;

public enum StatutEvenement {
	BROUILLON("B"),
	OUVERT("O"),
	FERME("F");
	
	private String name = "";
	
	StatutEvenement(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
