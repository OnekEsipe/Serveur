package com.onek.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Critere;
import com.onek.model.Descripteur;

@Repository
public class CritereDaoImpl implements CritereDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addCriteres(List<Critere> criteres) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (Critere critere : criteres) {
			session.save(critere);
			for (Descripteur descripteur : critere.getDescripteurs()) {
				session.save(descripteur);
			}
		}
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Critere findById(Integer id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Critere critere = (Critere) session.createQuery("FROM Critere WHERE idcritere = :id").setParameter("id", id)
				.getSingleResult();
		session.getTransaction().commit();
		session.close();
		return critere;
	}
}
