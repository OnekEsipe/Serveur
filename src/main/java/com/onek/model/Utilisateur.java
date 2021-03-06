package com.onek.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.onek.utils.DroitsUtilisateur;

/**
 * The persistent class for the utilisateurs database table.
 * 
 */
@Entity
@Table(name = "utilisateurs")
@NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iduser;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datetoken;

	private String droits;

	private Boolean isdeleted;

	private String login;

	private String mail;

	private String motdepasse;

	private String nom;

	private String prenom;

	private String token;

	// bi-directional many-to-one association to Evenement
	@OneToMany(mappedBy = "utilisateur")
	private List<Evenement> evenements;

	// bi-directional many-to-one association to Jury
	@OneToMany(mappedBy = "utilisateur")
	private List<Jury> jurys;

	public Utilisateur() {
	}

	public Integer getIduser() {
		return this.iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public Date getDatetoken() {
		return datetoken;
	}

	public void setDatetoken(Date datetoken) {
		this.datetoken = datetoken;
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

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
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
	
	public String getNomDroits() {
		if (getDroits().equals(DroitsUtilisateur.ADMINISTRATEUR.toString())) {
			return DroitsUtilisateur.ADMINISTRATEUR.getValue();
		}
		if (getDroits().equals(DroitsUtilisateur.ORGANISATEUR.toString())) {
			return DroitsUtilisateur.ORGANISATEUR.getValue();
		}
		if (getDroits().equals(DroitsUtilisateur.JURY.toString())) {
			return DroitsUtilisateur.JURY.getValue();
		}		
		return DroitsUtilisateur.ANONYME.getValue();
	}

	@Override
	public String toString() {
		return nom + " " + prenom;
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