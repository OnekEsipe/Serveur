package com.onek.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;
import com.onek.model.Evaluation;
import com.onek.model.Jury;

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
		session.getTransaction().commit();
		session.close();
		return evaluation;
	}
	@Override
	public void saveEvaluation(Candidat candidat, Jury jury) {
		
	}
}
