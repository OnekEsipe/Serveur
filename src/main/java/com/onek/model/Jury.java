package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jurys database table.
 * 
 */
@Entity
@Table(name="jurys")
@NamedQuery(name="Jury.findAll", query="SELECT j FROM Jury j")
public class Jury implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idjury;

	//bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy="jury")
	private List<Evaluation> evaluations;

	//bi-directional many-to-one association to Evenement
	@ManyToOne
	@JoinColumn(name="idevent")
	private Evenement evenement;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="iduser")
	private Utilisateur utilisateur;

	public Jury() {
	}

	public Integer getIdjury() {
		return this.idjury;
	}

	public void setIdjury(Integer idjury) {
		this.idjury = idjury;
	}

	public List<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Evaluation addEvaluation(Evaluation evaluation) {
		getEvaluations().add(evaluation);
		evaluation.setJury(this);

		return evaluation;
	}

	public Evaluation removeEvaluation(Evaluation evaluation) {
		getEvaluations().remove(evaluation);
		evaluation.setJury(null);

		return evaluation;
	}

	public Evenement getEvenement() {
		return this.evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}