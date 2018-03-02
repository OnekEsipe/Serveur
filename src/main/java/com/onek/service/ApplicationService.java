package com.onek.service;

import java.util.List;
import java.util.Optional;

import com.onek.resource.AccountResource;
import com.onek.resource.CodeEvenementResource;
import com.onek.resource.CreateJuryResource;
import com.onek.resource.EvaluationResource;
import com.onek.resource.EvenementResource;
import com.onek.resource.PasswordModify;

/**
 * Interface de la classe ApplicationServiceImpl. Couche service
 */
public interface ApplicationService {
	
	/**
	 * Crée et renvoi le json a exporter vers l'application pour l'événement demandé
	 * @param idEvent Id de l'événement
	 * @param login
	 * @return Json evenement
	 */
	public Optional<EvenementResource> export(String idEvent, String login);	
	
	/**
	 * Importe les évaluations depuis l'application
	 * @param evaluationResource Json evaluation
	 * @return True si le processus d'import s'est bien déroulé
	 */
	public boolean importEvaluation(EvaluationResource evaluationResource);
	
	/**
	 * Crée et renvoi un json contenant la liste des comptes pour l'application
	 * @param login
	 * @return La liste des comptes
	 */
	public List<AccountResource> account(String login);
	
	/**
	 * Importe un jury depuis l'application
	 * @param createJuryResource Json jury
	 */
	public void createJury(CreateJuryResource createJuryResource);
	
	/**
	 * Inscrit un utilisateur de l'application à un événement 
	 * @param eventCode Json code evenement
	 * @return True si l'inscription s'est bien déroulée
	 */
	public boolean subscribe(CodeEvenementResource eventCode);
	
	/**
	 * Réalise le changement de mot de passe à la demande d'un utilisateur de l'application
	 * @param passwordModify Json mot de passe modifié
	 * @return True si la modification de mot de passe s'est bien déroulée
	 */
	public boolean changePassword(PasswordModify passwordModify);
	
}
