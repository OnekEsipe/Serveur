package com.onek.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Critere;
import com.onek.model.Descripteur;

@Repository
public class CritereDaoImpl implements CritereDao, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(CritereDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addCriteres(List<Critere> criteres) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			for (Critere critere : criteres) {
				session.save(critere);
				for (Descripteur descripteur : critere.getDescripteurs()) {
					session.save(descripteur);
				}
			}
			transaction.commit();
			logger.info("Add criteria and descriptors done !");
		}
		catch(RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		}
		finally {
			session.close();
		}
	}

	@Override
	public Critere findById(Integer id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Critere critere = null;
		try {
			transaction = session.beginTransaction();
			critere = (Critere) session.createQuery("FROM Critere WHERE idcritere = :id").setParameter("id", id)
					.getSingleResult();
			Hibernate.initialize(critere.getDescripteurs());
			transaction.commit();
			logger.info("Find criteria by id done !");
			
		}
		catch(RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		}
		finally {
			session.close();			
		}	
		return critere;
	}

	@Override
	public void addCritere(Critere critere) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(critere);
		for (Descripteur descripteur : critere.getDescripteurs()) {
			session.save(descripteur);
		}
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void supprimerCritere(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Critere critere = findById(id);
		System.out.println(critere.getTexte()+" "+critere.getIdcritere());
		System.out.println(critere.getDescripteurs().size());
		for (Descripteur descripteur : critere.getDescripteurs()) {
			System.out.println("Descripteur : "+descripteur.getIddescripteur());
			session.createQuery("delete from Descripteur where iddescripteur = :iddescripteur")
			.setParameter("iddescripteur", descripteur.getIddescripteur()).executeUpdate();
		}
		session.createQuery("delete from Critere where idcritere = :idcritere")
		.setParameter("idcritere", id).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
}
