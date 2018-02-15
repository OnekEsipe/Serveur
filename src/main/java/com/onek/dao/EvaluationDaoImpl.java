package com.onek.dao;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Evaluation;

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

	@Override
	public void deleteEvaluation(int idJury, int idCandidat) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Evaluation evaluationToDelete = (Evaluation) session.createQuery("FROM Evaluation WHERE idjuryeval = :idJury AND idcandidat = :idCandidat").setParameter("idJury", idJury).setParameter("idCandidat", idCandidat).getSingleResult();
		session.delete(evaluationToDelete);
		session.getTransaction().commit();
		session.close();
	}
}
