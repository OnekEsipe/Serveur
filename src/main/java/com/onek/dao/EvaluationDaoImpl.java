package com.onek.dao;

import java.io.Serializable;
import java.util.List;

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
	public List<Evaluation> findByIdEvent(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<Evaluation> evaluations = (List<Evaluation>) session.createQuery("FROM Evaluation WHERE idevent = :id")
				.setParameter("id", id).list();

		for (Evaluation evaluation : evaluations) {
			Hibernate.initialize(evaluation.getUtilisateur());
			Hibernate.initialize(evaluation.getCandidat());
		}

		session.getTransaction().commit();
		session.close();

		System.out.println("Find done - Number of evaluations : " + evaluations.size());
		return evaluations;
	}

	@Override
	public boolean juryIsAssigned(int idJury, int idEvent) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		long result = (Long) session.createQuery("SELECT COUNT(e) from Evaluation e WHERE (idjury = :idj AND idevent = :ide)")
				.setParameter("idj", idJury).setParameter("ide", idEvent).getSingleResult();
		
		session.getTransaction().commit();
		session.close();
		
		return result > 0;
	}

}
