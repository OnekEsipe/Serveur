package com.onek.dao;

import com.onek.model.Candidat;
import java.util.Date;

import com.onek.model.Evaluation;
import java.util.List;
import com.onek.model.Jury;

/**
 * Interface de la classe EvaluationDaoImpl. Requêtes vers la base de données pour les evaluations
 */
public interface EvaluationDao {

	/**
	 * Récupére une évaluation par son identifiant.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id evaluation est inférieur à 1
	 * @param id Id de l'évaluation
	 * @return Evaluation
	 */
	Evaluation findById(int id);
	
	/**
	 * Met à jour une évaluation dans la base de données
	 * @param evaluation
	 */
	void update(Evaluation evaluation);
	
	/**
	 * Récupére la liste des évaluations d'un candidat.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id candidat est inférieur à 1
	 * @param id Id de l'évaluation
	 * @return Evaluations La liste des evaluations
	 */
	List<Evaluation> findByIdCandidate(int idCandidat);
	
	/**
	 * Récupére la liste des évaluations d'un jury.
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id jury est inférieur à 1
	 * @param idJury Id d'un jury
	 * @return Evaluations La liste des evaluations
	 */
	List<Evaluation> findByIdJury(int idJury);
	
	/**
	 * Supprime l'évaluation et les notes associées dans la base de données
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id jury ou l'id candidat est inférieur à 1
	 * @param idJury Id d'un jury
	 * @param idCandidat Id d'un candidat
	 */
	void deleteEvaluation(int idJury, int idCandidat);
	
	/**
	 * Sauvegarde de l'évaluation dans la base de données. Crée des notes initialisées à -1 pour chaque critères de l'événement
	 * Une erreur de type RuntimeException entraine le rollback
	 * @exception IllegalArgumentException Si l'id evenement est inférieur à 1
	 * @param candidat Le candidat
	 * @param jury Le jury
	 * @param date La date
	 * @param idevent L'id de l'événement
	 */
	public void saveEvaluation(Candidat candidat, Jury jury, Date date, int idevent);
}
