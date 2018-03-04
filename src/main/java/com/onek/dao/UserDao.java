package com.onek.dao;

import java.util.List;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;

/**
 * Interface de la classe UserDaoImpl. Requêtes vers la base de données pour les users
 */
public interface UserDao {
  
	/**
	 * Enregistre un utilisateur dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param Utilisateur Un utilisateur
	 */
	public void addUser(Utilisateur user);
	
	/**
	 * Passe le champ isdeleted à true dans la base de données. L'utilisateur est marqué comme supprimé et peut être réactivé.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id utilisateur est inférieur à 1
	 * @param idUser Id de l'utilisateur
	 */
	public void deleteUser(int idUser) ;
	
	/**
	 * Compte l'occurence d'un utilisateur par le login dans la base de données. 
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param login Login de l'utilisateur
	 * @return True si l'utilisateur existe
	 */
	public boolean userExist(String login);
	
	/**
	 * Mets à jour un utilisateur dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param user Utilisateur à mettre à jour
	 */
	public void updateUserInfos(Utilisateur user);
	
	/**
	 * Ajoute une liste d'utilisateurs anonymes dans la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalStateException Si la liste est vide
	 * @param utilisateurs La liste des utilisateurs anonymes a ajouter
	 * @param event L'événement
	 */
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event);
	
	/**
	 * Récupère un utilisateur par le login depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param login Le login de l'utilisateur à rechercher
	 * @return L'utilisateur trouvé
	 */
	public Utilisateur findByLogin(String login);
	
	/**
	 * Récupère les utilisateurs depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @return La liste des utilisateurs 
	 */
	public List<Utilisateur> getAllUsers();
	
	/**
	 * Récupère les utilisateurs, or utilisateurs anonymes et utilisateurs courants, depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @return La liste des utilisateurs 
	 */
	public List<Utilisateur> getAllUsersExceptCurrentAndAnonymous(int idcurrentUser);
	
	/**
	 * Compte l'occurence d'un mail dans la base de données. 
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param mail Le mail a chercher
	 * @return True si le mail existe
	 */
	public boolean mailExist(String mail);
	
	/**
	 * Récupère un utilisateur par le mail depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param mail Le mail de l'utilisateur à rechercher
	 * @return L'utilisateur trouvé
	 */
	public Utilisateur findByMail(String mail);
	
	/**
	 * Récupère un utilisateur par l'id utilisateur depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param iduser L'id de l'utilisateur à rechercher
	 * @return L'utilisateur trouvé
	 */
	public Utilisateur findUserById(int iduser);
	
	/**
	 * Récupère un utilisateur par son token depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @param token Le token de l'utilisateur à rechercher
	 * @return L'utilisateur trouvé
	 */
	public Utilisateur findByToken(String token);

  /**
	 * Récupère les utilisateurs, or utilisateurs supprimés et anonymes, depuis la base de données.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @return La liste des utilisateurs 
	 */
	public List<Utilisateur> getAllUsersExceptDeletedansAno();

}
