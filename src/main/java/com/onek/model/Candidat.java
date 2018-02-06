package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the candidats database table.
 * 
 */
@Entity
@Table(name="candidats")
@NamedQuery(name="Candidat.findAll", query="SELECT c FROM Candidat c")
public class Candidat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idcandidat;

	private String nom;

	private String prenom;

	//bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy="candidat")
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

}