package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the evaluations database table.
 * 
 */
@Entity
@Table(name="evaluations")
@NamedQuery(name="Evaluation.findAll", query="SELECT e FROM Evaluation e")
public class Evaluation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer idevaluation;

	private String commentaire;

	@Temporal(TemporalType.DATE)
	private Date datedernieremodif;

	private byte[] signature;

	//bi-directional many-to-one association to Candidat
	@ManyToOne
	@JoinColumn(name="idcandidat")
	private Candidat candidat;

	//bi-directional many-to-one association to Evenement
	@ManyToOne
	@JoinColumn(name="idevent")
	private Evenement evenement;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="idjury")
	private Utilisateur utilisateur;

	//bi-directional many-to-one association to Note
	@OneToMany(mappedBy="evaluation")
	private List<Note> notes;

	public Evaluation() {
	}

	public Integer getIdevaluation() {
		return this.idevaluation;
	}

	public void setIdevaluation(Integer idevaluation) {
		this.idevaluation = idevaluation;
	}

	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDatedernieremodif() {
		return this.datedernieremodif;
	}

	public void setDatedernieremodif(Date datedernieremodif) {
		this.datedernieremodif = datedernieremodif;
	}

	public byte[] getSignature() {
		return this.signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public Candidat getCandidat() {
		return this.candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
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

	public List<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Note addNote(Note note) {
		getNotes().add(note);
		note.setEvaluation(this);

		return note;
	}

	public Note removeNote(Note note) {
		getNotes().remove(note);
		note.setEvaluation(null);

		return note;
	}

}