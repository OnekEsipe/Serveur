package com.onek.dao;

import java.util.HashMap;
import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;

/**
 * Interface de la classe JuryDaoImpl. Requêtes vers la base de données pour les jurys
 */
public interface JuryDao {
	
	/**
	 * Informe avec un boolean si un jury est assigné à un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si idUser ou idEvent inférieur à 1
	 * @param idUser Id de l'utilisateur
	 * @param idEvent Id de l'événement
	 * @return boolean si le jury est assigné
	 */
	public boolean juryIsAssigned(int idUser, int idEvent);
	 
	/**
	 * Retourne la liste des jurys et jurys anonymes d'un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si idEvent inférieur à 1
	 * @param idEvent Id de l'événement
	 * @param login Login du jury
	 * @return La liste des jurys et jurys anonymes
	 */
	public List<Jury> findJuryAndAnonymousByIdEvent(int idEvent, String login);
	
	/**
	 * Retourne la liste des jurys anonymes d'un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si idEvent inférieur à 1
	 * @param idEvent Id de l'événement
	 * @return La liste des jurys anonymes
	 */
	public List<Jury> findAnonymousByIdEvent(int idEvent);
	
	/**
	 * Retourne la liste des jurys anonymes d'un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @param user Utilisateur
	 * @return La liste des jurys par utilisateur
	 */
	public List<Jury> findByUser(Utilisateur user);
	
	/**
	 * Retourne la liste des jurys d'un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si idEvent inférieur à 1
	 * @param idEvent Id de l'événement
	 * @return La liste des jurys 
	 */
	public List<Jury> findJurysByIdevent(int idevent);
	
	/**
	 * Récupère les associations jurys-candidats depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalStateException Si la liste de candidats est vide
	 * @exception IllegalArgumentException Si idEvent inférieur à 1
	 * @param jurys Liste des jurys
	 * @param idevent Id de l'événément
	 * @return Map des jurys associés aux candidats pour un événement
	 */
	public HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent);
	
	/**
	 * Récupère la liste des utilisateurs d'un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si idEvent inférieur à 1
	 * @param idevent Id de l'événément
	 * @return La liste des utilisateurs
	 */
	public List<Utilisateur> listJurysByEvent(int idevent);
	
	/**
	 * Récupère la liste des utilisateurs anonymes d'un événement depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si idEvent inférieur à 1
	 * @param idevent Id de l'événément
	 * @return La liste des utilisateurs anonymes
	 */
	public List<Utilisateur> findJurysAnnonymesByEvent(int idevent);
	
	/**
	 * Supprime un utilisateur dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si idevent inférieur à 1
	 * @exception IllegalArgumentException Si iduser inférieur à 1
	 * @param iduser Id de l'utilisateur
	 * @param idevent Id de l'événément
	 */
	public void supprimerUtilisateur(int iduser, int idevent);
	
	/** Ajoute un jury dans la base de donnée.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @param jury 
	 */
	public void addJuryToEvent(Jury jury);
	
	/**
	 * Ajoute une liste de jurys dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalStateException Si la liste est vide
	 * @param jurys
	 */
	public void addListJurys(List<Jury> jurys);
	
	/**
	 * Récupère un utilisateur par l'id depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si iduser inférieur à 1
	 * @param id Id de l'utilisateur
	 * @return L'utilisateur correspondant à l'id
	 */
	public Utilisateur findById(int id) ;
	
	/**
	 * Supprime un utilisateur anonyme dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback.
	 * @exception IllegalArgumentException Si iduser inférieur à 1
	 * @param iduser Id de l'utilisateur
	 */
	public void supprimerUtilisateurAnonyme(int iduser);
}
