package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the descripteurs database table.
 * 
 */
@Entity
@Table(name = "descripteurs")
@NamedQuery(name = "Descripteur.findAll", query = "SELECT d FROM Descripteur d")
public class Descripteur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iddescripteur;

	private String niveau;

	private BigDecimal poids;

	@Column(length = 500)
	private String texte;

	// bi-directional many-to-one association to Critere
	@ManyToOne
	@JoinColumn(name = "idcritere")
	private Critere critere;

	public Descripteur() {
	}

	public Integer getIddescripteur() {
		return this.iddescripteur;
	}

	public void setIddescripteur(Integer iddescripteur) {
		this.iddescripteur = iddescripteur;
	}

	public String getNiveau() {
		return this.niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public BigDecimal getPoids() {
		return this.poids;
	}

	public void setPoids(BigDecimal poids) {
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

	@Override
	public String toString() {
		return niveau + "\n" + "(Poids = " + poids + "\n ," + "Description = " + texte + ")";
	}

}
