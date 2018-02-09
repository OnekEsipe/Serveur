package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the notes database table.
 * 
 */
@Entity
@Table(name="notes")
@NamedQuery(name="Note.findAll", query="SELECT n FROM Note n")
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idnote;

	private String commentaire;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Integer niveau;

	//bi-directional many-to-one association to Critere
	@ManyToOne
	@JoinColumn(name="idcritere")
	private Critere critere;

	//bi-directional many-to-one association to Evaluation
	@ManyToOne
	@JoinColumn(name="idevaluation")
	private Evaluation evaluation;

	public Note() {
	}

	public Integer getIdnote() {
		return this.idnote;
	}

	public void setIdnote(Integer idnote) {
		this.idnote = idnote;
	}

	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNiveau() {
		return this.niveau;
	}

	public void setNiveau(Integer niveau) {
		this.niveau = niveau;
	}

	public Critere getCritere() {
		return this.critere;
	}

	public void setCritere(Critere critere) {
		this.critere = critere;
	}

	public Evaluation getEvaluation() {
		return this.evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

}