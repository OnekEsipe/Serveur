package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the utilisateurs database table.
 * 
 */
@Entity
@Table(name="utilisateurs")
@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer iduser;

	private String droits;

	private Boolean isdeleted;

	private String login;

	private String mail;

	private String motdepasse;

	private String nom;

	private String prenom;

	//bi-directional many-to-one association to Evenement
	@OneToMany(mappedBy="utilisateur")
	private List<Evenement> evenements;

	//bi-directional many-to-one association to Jury
	@OneToMany(mappedBy="utilisateur")
	private List<Jury> jurys;

	public Utilisateur() {
	}

	public Integer getIduser() {
		return this.iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public String getDroits() {
		return this.droits;
	}

	public void setDroits(String droits) {
		this.droits = droits;
	}

	public Boolean getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMotdepasse() {
		return this.motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
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

	public List<Evenement> getEvenements() {
		return this.evenements;
	}

	public void setEvenements(List<Evenement> evenements) {
		this.evenements = evenements;
	}

	public Evenement addEvenement(Evenement evenement) {
		getEvenements().add(evenement);
		evenement.setUtilisateur(this);

		return evenement;
	}

	public Evenement removeEvenement(Evenement evenement) {
		getEvenements().remove(evenement);
		evenement.setUtilisateur(null);

		return evenement;
	}

	public List<Jury> getJurys() {
		return this.jurys;
	}

	public void setJurys(List<Jury> jurys) {
		this.jurys = jurys;
	}

	public Jury addJury(Jury jury) {
		getJurys().add(jury);
		jury.setUtilisateur(this);

		return jury;
	}

	public Jury removeJury(Jury jury) {
		getJurys().remove(jury);
		jury.setUtilisateur(null);

		return jury;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iduser == null) ? 0 : iduser.hashCode());
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
		Utilisateur other = (Utilisateur) obj;
		if (iduser == null) {
			if (other.iduser != null)
				return false;
		} else if (!iduser.equals(other.iduser))
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