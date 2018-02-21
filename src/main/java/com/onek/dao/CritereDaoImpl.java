package com.onek.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
		Objects.requireNonNull(criteres);
		if(criteres.isEmpty()) {
			throw new IllegalArgumentException("list must not be empty");
		}
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
			logger.info("Add criterias and descriptors done !");
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
		if(id < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
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
		Objects.requireNonNull(critere);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(critere);
			for (Descripteur descripteur : critere.getDescripteurs()) {
				session.save(descripteur);
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
	public void supprimerCritere(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Critere critere = findById(id);		
			for (Descripteur descripteur : critere.getDescripteurs()) {			
				session.createQuery("delete from Descripteur where iddescripteur = :iddescripteur")
				.setParameter("iddescripteur", descripteur.getIddescripteur()).executeUpdate();
			}
			session.createQuery("delete from Critere where idcritere = :idcritere")
			.setParameter("idcritere", id).executeUpdate();
			transaction.commit();
			logger.info("Delete criteria and descriptors done !");
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
}
