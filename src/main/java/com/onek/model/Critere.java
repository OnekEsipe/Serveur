package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the criteres database table.
 * 
 */
@Entity
@Table(name = "criteres")
@NamedQuery(name = "Critere.findAll", query = "SELECT c FROM Critere c")
public class Critere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcritere;

	private String categorie;

	private BigDecimal coefficient;

	private String texte;

	// bi-directional many-to-one association to Evenement
	@ManyToOne
	@JoinColumn(name = "idevent")
	private Evenement evenement;

	// bi-directional many-to-one association to Note
	@OneToMany(mappedBy = "critere")
	private List<Note> notes;

	// bi-directional many-to-one association to Descripteur
	@OneToMany(mappedBy = "critere")
	private List<Descripteur> descripteurs;

	public Critere() {
	}

	public Integer getIdcritere() {
		return this.idcritere;
	}

	public void setIdcritere(Integer idcritere) {
		this.idcritere = idcritere;
	}

	public String getCategorie() {
		return this.categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public BigDecimal getCoefficient() {
		return this.coefficient;
	}

	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}

	public String getTexte() {
		return this.texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Evenement getEvenement() {
		return this.evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	public List<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Note addNote(Note note) {
		getNotes().add(note);
		note.setCritere(this);

		return note;
	}

	public Note removeNote(Note note) {
		getNotes().remove(note);
		note.setCritere(null);

		return note;
	}

	public List<Descripteur> getDescripteurs() {
		return this.descripteurs;
	}

	public void setDescripteurs(List<Descripteur> descripteurs) {
		this.descripteurs = descripteurs;
	}

	public Descripteur addDescripteur(Descripteur descripteur) {
		getDescripteurs().add(descripteur);
		descripteur.setCritere(this);

		return descripteur;
	}

	public Descripteur removeDescripteur(Descripteur descripteur) {
		getDescripteurs().remove(descripteur);
		descripteur.setCritere(null);

		return descripteur;
	}

	public String printDescripteurs() {
		StringBuilder sb = new StringBuilder();
		for (Descripteur descripteur : descripteurs) {
			sb.append(descripteur.toString()).append("\n").append(" ");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idcritere == null) ? 0 : idcritere.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Critere other = (Critere) obj;
		if (idcritere == null) {
			if (other.idcritere != null)
				return false;
		} else if (!idcritere.equals(other.idcritere))
			return false;
		return true;
	}

}