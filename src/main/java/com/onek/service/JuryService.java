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
	 * @see com.onek.dao.JuryDao#associatedJurysCandidatesByEvent(java.util.ist, int)
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
	 * @see com.onek.dao.JuryDao#findAnonymousByIdEvent(int)
	 */
	public List<Jury> findAnonymousByIdEvent(int idEvent);
	
	/**
	 * @see com.onek.dao.JuryDao#addListJurys(java.util.List)
	 */
	public void addListJurys(List<Jury> jurys);
	
	/**
	 * @see com.onek.dao.JuryDao#findById(int)
	 */
	public Utilisateur findById(int id) ;
	
	/**
	 * @see com.onek.dao.JuryDao#supprimerUtilisateurAnonyme(int)
	 */
  public void supprimerUtilisateurAnonyme(int iduser);
	public Jury findJuryById(int id);
	public List<Jury> findJuryAndAnonymousByIdEvent(int idEvent, String login);
	public List<Jury> findByUser(Utilisateur user);
	public List<Utilisateur> findJurysAnnonymesByEvent(int idevent);

}
