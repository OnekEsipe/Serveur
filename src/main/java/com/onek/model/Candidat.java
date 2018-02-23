package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the candidats database table.
 * 
 */
@Entity
@Table(name = "candidats")
@NamedQuery(name = "Candidat.findAll", query = "SELECT c FROM Candidat c")
public class Candidat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcandidat;

	private String nom;

	private String prenom;

	// bi-directional many-to-one association to Evenement
	@ManyToOne
	@JoinColumn(name = "idevent")
	private Evenement evenement;

	// bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy = "candidat")
	private List<Evaluation> evaluations;

	public Candidat() {
	}

	public Integer getIdcandidat() {
		return this.idcandidat;
	}

	public void setIdcandidat(Integer idcandidat) {
		this.idcandidat = idcandidat;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Evenement getEvenement() {
		return this.evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	public List<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Evaluation addEvaluation(Evaluation evaluation) {
		getEvaluations().add(evaluation);
		evaluation.setCandidat(this);

		return evaluation;
	}

	public Evaluation removeEvaluation(Evaluation evaluation) {
		getEvaluations().remove(evaluation);
		evaluation.setCandidat(null);

		return evaluation;
	}

	@Override
	public String toString() {
		return nom + " " + prenom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idcandidat == null) ? 0 : idcandidat.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
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
		Candidat other = (Candidat) obj;
		if (idcandidat == null) {
			if (other.idcandidat != null)
				return false;
		} else if (!idcandidat.equals(other.idcandidat))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}
}