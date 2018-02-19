package com.onek.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Evaluation;
import com.onek.model.Jury;
import com.onek.model.Note;

@Repository
public class EvaluationDaoImpl implements EvaluationDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Evaluation findById(Integer id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Evaluation evaluation = (Evaluation) session.createQuery("FROM Evaluation WHERE idevaluation = :id")
				.setParameter("id", id).getSingleResult();
						Hibernate.initialize(evaluation.getNotes());
		session.getTransaction().commit();
		session.close();
		return evaluation;
	}
	
	@Override
	public void update(Evaluation evaluation) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(evaluation);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> findByIdCandidate(Integer idCandidat) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Evaluation> evaluations = (List<Evaluation>) session.createQuery("FROM Evaluation WHERE idcandidat = :id").setParameter("id", idCandidat).list();
		for (Evaluation evaluation : evaluations) {
			Hibernate.initialize(evaluation.getNotes());
		}
		session.getTransaction().commit();
		session.close();
		return evaluations;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> findByIdJury(Integer idJury) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Evaluation> evaluations = (List<Evaluation>) session.createQuery("FROM Evaluation WHERE idjuryeval = :id").setParameter("id", idJury).list();
		for (Evaluation evaluation : evaluations) {
			Hibernate.initialize(evaluation.getNotes());
		}
		session.getTransaction().commit();
		session.close();
		return evaluations;
	}
  
	@SuppressWarnings("unchecked")
	@Override
	public void deleteEvaluation(int idJury, int idCandidat) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Evaluation evaluationToDelete = (Evaluation) session
				.createQuery("FROM Evaluation WHERE idjuryeval = :idJury AND idcandidat = :idCandidat")
				.setParameter("idJury", idJury).setParameter("idCandidat", idCandidat).getSingleResult();
		List<Note> notesToDelete = (List<Note>) session.createQuery("FROM Note WHERE idevaluation = :idevaluation").setParameter("idevaluation", evaluationToDelete.getIdevaluation()).getResultList();
		for(Note note : notesToDelete) {
			session.delete(note);
		}
		session.delete(evaluationToDelete);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury, Date date) {
		System.out.println(nomCandidat + " " + prenomCandidat);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		//Creation de l'evaluation 
		Candidat candidat = (Candidat) session.createQuery("FROM Candidat WHERE idevent = :idevent AND nom = :nomCandidat AND prenom = :prenomCandidat")
				.setParameter("idevent", idevent).setParameter("nomCandidat", nomCandidat)
				.setParameter("prenomCandidat", prenomCandidat).getSingleResult();
		Evaluation evaluation = new Evaluation();
		evaluation.setCandidat(candidat);
		evaluation.setCommentaire("");
		evaluation.setDatedernieremodif(date);
		evaluation.setJury(jury);
		evaluation.setSignature(new byte[0]);
		session.save(evaluation);
		
		//Creation de notes (initialisé à -1) pour chaque criteres de l'evenement
		List<Critere> criteres = (List<Critere>) session.createQuery("FROM Critere WHERE idevent = :idevent")
				.setParameter("idevent", idevent).getResultList();
		for(Critere critere : criteres) {
			Note note = new Note();
			note.setCommentaire("");
			note.setCritere(critere);
			note.setDate(date);
			note.setEvaluation(evaluation);
			note.setNiveau(-1);
			session.save(note);
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveEvaluation(Candidat candidat, Jury jury, Date date, int idevent) {
		
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			//Creation de l'evaluation 
			Evaluation evaluation = new Evaluation();
			evaluation.setCandidat(candidat);
			evaluation.setCommentaire("");
			evaluation.setDatedernieremodif(date);
			evaluation.setJury(jury);
			evaluation.setSignature(new byte[0]);
			session.save(evaluation);
			
			//Creation de notes (initialisé à -1) pour chaque criteres de l'evenement
			List<Critere> criteres = (List<Critere>) session.createQuery("FROM Critere WHERE idevent = :idevent")
					.setParameter("idevent", idevent).getResultList();
			for(Critere critere : criteres) {
				Note note = new Note();
				note.setCommentaire("");
				note.setCritere(critere);
				note.setDate(date);
				note.setEvaluation(evaluation);
				note.setNiveau(-1);
				session.save(note);
			}
			
			session.getTransaction().commit();
			session.close();
		
	}
}
