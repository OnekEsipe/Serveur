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
@Table(name = "evaluations")
@NamedQuery(name = "Evaluation.findAll", query = "SELECT e FROM Evaluation e")
public class Evaluation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idevaluation;

	@Column(length = 500)
	private String commentaire;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datedernieremodif;

	private Boolean issigned;

	// bi-directional many-to-one association to Candidat
	@ManyToOne
	@JoinColumn(name = "idcandidat")
	private Candidat candidat;

	// bi-directional many-to-one association to Jury
	@ManyToOne
	@JoinColumn(name = "idjuryeval")
	private Jury jury;

	// bi-directional many-to-one association to Note
	@OneToMany(mappedBy = "evaluation")
	private List<Note> notes;

	// bi-directional many-to-one association to Signature
	@OneToMany(mappedBy = "evaluation")
	private List<Signature> signatures;

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

	public Boolean getIssigned() {
		return this.issigned;
	}

	public void setIssigned(Boolean issigned) {
		this.issigned = issigned;
	}

	public Candidat getCandidat() {
		return this.candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public Jury getJury() {
		return this.jury;
	}

	public void setJury(Jury jury) {
		this.jury = jury;
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

	public List<Signature> getSignatures() {
		return this.signatures;
	}

	public void setSignatures(List<Signature> signatures) {
		this.signatures = signatures;
	}

	public Signature addSignature(Signature signature) {
		getSignatures().add(signature);
		signature.setEvaluation(this);

		return signature;
	}

	public Signature removeSignature(Signature signature) {
		getSignatures().remove(signature);
		signature.setEvaluation(null);

		return signature;
	}

}