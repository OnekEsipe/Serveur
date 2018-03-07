package com.onek.service;

import java.util.HashMap;
import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;


/**
 * Interface de la classe JuryServiceImpl. Couche service
 */
public interface JuryService {
	
	/**
	 * @see com.onek.dao.JuryDao#findJurysByIdevent(int)
	 */
	List<Jury> findJurysByIdevent(int idevent);
	
	/**
	 * Récupère les associations jurys-candidats depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalStateException Si la liste de candidats est vide
	 * @exception IllegalArgumentException Si idEvent inférieur à 1
	 * @param jurys Liste des jurys
	 * @param idevent Id de l'événément
	 * @return Map des jurys associés aux candidats pour un événement
	 */
	HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent);
	
	/**
	 * @see com.onek.dao.JuryDao#listJurysByEvent(int)
	 */
	public List<Utilisateur> listJurysByEvent(int idevent);
	
	/**
	 * @see com.onek.dao.JuryDao#findAnonymousByIdEvent(int)
	 */
	public List<Jury> listJurysAnnonymesByEvent(int idevent) ;
	
	/**
	 * @see com.onek.dao.UserDao#getAllUsers()
	 */
	public List<Utilisateur> findAllJurys();
	
	/**
	 * @see com.onek.dao.JuryDao#supprimerUtilisateur(int, int)
	 */
	public void supprimerUtilisateur(int iduser,int idevent);
	
	/**
	 * @see com.onek.dao.JuryDao#addJuryToEvent(Jury)
	 */
	public void addJuryToEvent(Jury jury);
	
	/**
	 * @see com.onek.dao.JuryDao#addListJurys(java.util.List)
	 */
	public void addListJurys(List<Jury> jurys);
	
	/**
	 * @see com.onek.dao.JuryDao#findById(int)
	 */
	public Utilisateur findById(int id) ;

	/**
	 * @see com.onek.dao.JuryDao#findJuryById(int)
	 */
	public Jury findJuryById(int id);
	
	/**
	 * @see com.onek.dao.JuryDao#supprimerUtilisateurAnonyme(int)
	 */
	public void supprimerUtilisateurAnonyme(int iduser);
	
	/**
	 * @see com.onek.dao.JuryDao#findJuryAndAnonymousByIdEvent(int, String)
	 */
	public List<Jury> findJuryAndAnonymousByIdEvent(int idEvent, String login);
	
	/**
	 * @see com.onek.dao.JuryDao#findByUser(Utilisateur)
	 */
	public List<Jury> findByUser(Utilisateur user);
	
	/**
	 * @see com.onek.dao.JuryDao#findJurysAnnonymesByEvent(int)
	 */
	public List<Utilisateur> findJurysAnnonymesByEvent(int idevent);
}
