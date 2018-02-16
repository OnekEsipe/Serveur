package com.onek.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;
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

	@Override
	public void saveEvaluation(String nomCandidat, String prenomCandidat, int idevent, Jury jury) {
		System.out.println(nomCandidat + " " + prenomCandidat);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Candidat candidat = (Candidat) session.createQuery("FROM Candidat WHERE idevent = :idevent AND nom = :nomCandidat AND prenom = :prenomCandidat")
				.setParameter("idevent", idevent).setParameter("nomCandidat", nomCandidat)
				.setParameter("prenomCandidat", prenomCandidat).getSingleResult();
		Evaluation evaluation = new Evaluation();
		evaluation.setJury(jury);
		evaluation.setCandidat(candidat);
		session.save(evaluation);
		session.getTransaction().commit();
		session.close();
	}
}
