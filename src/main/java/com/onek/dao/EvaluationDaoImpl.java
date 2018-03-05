package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Evaluation;
import com.onek.model.Jury;
import com.onek.model.Note;

/**
 * Dao du model Evaluation
 */
@Repository
public class EvaluationDaoImpl implements EvaluationDao, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(EvaluationDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Evaluation findById(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Evaluation evaluation = null;
		try {
			transaction = session.beginTransaction();
			evaluation = (Evaluation) session.createQuery("FROM Evaluation WHERE idevaluation = :id")
					.setParameter("id", id).getSingleResult();
			Hibernate.initialize(evaluation.getNotes());
			transaction.commit();
			logger.info("Find evaluation by id done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return evaluation;
	}

	@Override
	public void update(Evaluation evaluation) {
		Objects.requireNonNull(evaluation);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(evaluation);
			transaction.commit();
			logger.info("Update evaluation done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> findByIdCandidate(int idCandidat) {
		if(idCandidat < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Evaluation> evaluations = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
			evaluations = (List<Evaluation>) session.createQuery("FROM Evaluation WHERE idcandidat = :id")
					.setParameter("id", idCandidat).list();
			for (Evaluation evaluation : evaluations) {
				Hibernate.initialize(evaluation.getNotes());
				Hibernate.initialize(evaluation.getSignatures());
			}
			transaction.commit();
			logger.info("Find evaluation by id candidate done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return evaluations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> findByIdJury(int idJury) {
		if(idJury < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Evaluation> evaluations = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
			evaluations = (List<Evaluation>) session.createQuery("FROM Evaluation WHERE idjuryeval = :id")
					.setParameter("id", idJury).list();
			for (Evaluation evaluation : evaluations) {
				Hibernate.initialize(evaluation.getNotes());
			}
			transaction.commit();
			logger.info("Find evaluation by id jury done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return evaluations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteEvaluation(int idJury, int idCandidat) {
		if(idJury < 1 || idCandidat < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Evaluation evaluationToDelete = (Evaluation) session
					.createQuery("FROM Evaluation WHERE idjuryeval = :idJury AND idcandidat = :idCandidat")
					.setParameter("idJury", idJury).setParameter("idCandidat", idCandidat).getSingleResult();
			List<Note> notesToDelete = (List<Note>) session.createQuery("FROM Note WHERE idevaluation = :idevaluation")
					.setParameter("idevaluation", evaluationToDelete.getIdevaluation()).getResultList();
			for (Note note : notesToDelete) {
				session.delete(note);
			}
			session.delete(evaluationToDelete);
			transaction.commit();
			logger.info("Delete evaluation done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveEvaluation(Candidat candidat, Jury jury, Date date, int idevent) {
		Objects.requireNonNull(candidat);
		Objects.requireNonNull(jury);
		Objects.requireNonNull(date);
		if(idevent < 1) {
			throw new IllegalArgumentException("id must be positive");
		}

		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			// Creation de l'evaluation
			Evaluation evaluation = new Evaluation();
			evaluation.setCandidat(candidat);
			evaluation.setCommentaire("");
			evaluation.setDatedernieremodif(date);
			evaluation.setJury(jury);
			evaluation.setIssigned(false);
			session.save(evaluation);

			// Creation de notes (initialisé à -1) pour chaque criteres de l'evenement
			List<Critere> criteres = (List<Critere>) session.createQuery("FROM Critere WHERE idevent = :idevent")
					.setParameter("idevent", idevent).getResultList();
			for (Critere critere : criteres) {
				Note note = new Note();
				note.setCommentaire("");
				note.setCritere(critere);
				note.setDate(date);
				note.setEvaluation(evaluation);
				note.setNiveau(-1);
				session.save(note);
			}
			transaction.commit();
			logger.info("Add evaluation done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
	}
}
