package com.onek.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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

	private String code;

	@Temporal(TemporalType.DATE)
	private Date datestart;

	@Temporal(TemporalType.DATE)
	private Date datestop;

	private Boolean isopened;

	private Boolean issigned;

	private String nom;

	private String status;

	//bi-directional many-to-one association to Candidat
	@OneToMany(mappedBy="evenement")
	private List<Candidat> candidats;

	//bi-directional many-to-one association to Critere
	@OneToMany(mappedBy="evenement")
	private List<Critere> criteres;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="iduser")
	private Utilisateur utilisateur;

	//bi-directional many-to-one association to Jury
	@OneToMany(mappedBy="evenement")
	private List<Jury> jurys;

	public Evenement() {
	}

	public Integer getIdevent() {
		return this.idevent;
	}

	public void setIdevent(Integer idevent) {
		this.idevent = idevent;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<Candidat> getCandidats() {
		return this.candidats;
	}

	public void setCandidats(List<Candidat> candidats) {
		this.candidats = candidats;
	}

	public Candidat addCandidat(Candidat candidat) {
		getCandidats().add(candidat);
		candidat.setEvenement(this);

		return candidat;
	}

	public Candidat removeCandidat(Candidat candidat) {
		getCandidats().remove(candidat);
		candidat.setEvenement(null);

		return candidat;
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

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Jury> getJurys() {
		return this.jurys;
	}

	public void setJurys(List<Jury> jurys) {
		this.jurys = jurys;
	}

	public Jury addJury(Jury jury) {
		getJurys().add(jury);
		jury.setEvenement(this);

		return jury;
	}

	public Jury removeJury(Jury jury) {
		getJurys().remove(jury);
		jury.setEvenement(null);

		return jury;
	}

}