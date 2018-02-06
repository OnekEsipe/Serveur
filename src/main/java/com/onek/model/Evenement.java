package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the evenements database table.
 * 
 */
@Entity
@Table(name="evenements")
@NamedQuery(name="Evenement.findAll", query="SELECT e FROM Evenement e")
public class Evenement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idevent;

	@Temporal(TemporalType.DATE)
	private Date datestart;

	@Temporal(TemporalType.DATE)
	private Date datestop;

	private Boolean isopened;

	private Boolean issigned;

	private String nom;

	private String status;

	//bi-directional many-to-one association to Critere
	@OneToMany(mappedBy="evenement")
	private List<Critere> criteres;

	//bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy="evenement")
	private List<Evaluation> evaluations;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="iduser")
	private Utilisateur utilisateur;

	public Evenement() {
	}

	public Integer getIdevent() {
		return this.idevent;
	}

	public void setIdevent(Integer idevent) {
		this.idevent = idevent;
	}

	public Date getDatestart() {
		return this.datestart;
	}

	public void setDatestart(Date datestart) {
		this.datestart = datestart;
	}

	public Date getDatestop() {
		return this.datestop;
	}

	public void setDatestop(Date datestop) {
		this.datestop = datestop;
	}

	public Boolean getIsopened() {
		return this.isopened;
	}

	public void setIsopened(Boolean isopened) {
		this.isopened = isopened;
	}

	public Boolean getIssigned() {
		return this.issigned;
	}

	public void setIssigned(Boolean issigned) {
		this.issigned = issigned;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Critere> getCriteres() {
		return this.criteres;
	}

	public void setCriteres(List<Critere> criteres) {
		this.criteres = criteres;
	}

	public Critere addCritere(Critere critere) {
		getCriteres().add(critere);
		critere.setEvenement(this);

		return critere;
	}

	public Critere removeCritere(Critere critere) {
		getCriteres().remove(critere);
		critere.setEvenement(null);

		return critere;
	}

	public List<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Evaluation addEvaluation(Evaluation evaluation) {
		getEvaluations().add(evaluation);
		evaluation.setEvenement(this);

		return evaluation;
	}

	public Evaluation removeEvaluation(Evaluation evaluation) {
		getEvaluations().remove(evaluation);
		evaluation.setEvenement(null);

		return evaluation;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}