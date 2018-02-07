package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the descripteur database table.
 * 
 */
@Entity
@NamedQuery(name="Descripteur.findAll", query="SELECT d FROM Descripteur d")
public class Descripteur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer iddescripteur;

	private Integer niveau;

	private Integer poids;

	private String texte;

	//bi-directional many-to-one association to Critere
	@ManyToOne
	@JoinColumn(name="idcritere")
	private Critere critere;

	public Descripteur() {
	}

	public Integer getIddescripteur() {
		return this.iddescripteur;
	}

	public void setIddescripteur(Integer iddescripteur) {
		this.iddescripteur = iddescripteur;
	}

	public Integer getNiveau() {
		return this.niveau;
	}

	public void setNiveau(Integer niveau) {
		this.niveau = niveau;
	}

	public Integer getPoids() {
		return this.poids;
	}

	public void setPoids(Integer poids) {
		this.poids = poids;
	}

	public String getTexte() {
		return this.texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Critere getCritere() {
		return this.critere;
	}

	public void setCritere(Critere critere) {
		this.critere = critere;
	}

}