package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.onek.model.Evenement;

/**
 * Dao du model Evenement
 */
@Repository
public class EvenementDaoImpl implements EvenementDao, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(EvenementDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Evenement addEvenement(Evenement event) {
		Objects.requireNonNull(event);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(event);	
			transaction.commit();
			logger.info("Duplicate event done ! ");
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
		return event;
	}

	@Override
	public Evenement findById(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Evenement event = null;
		try {
			transaction = session.beginTransaction();
			event = (Evenement) session.createQuery("FROM Evenement WHERE idevent = :id").setParameter("id", id)
					.getSingleResult();
			Hibernate.initialize(event.getCriteres());
			Hibernate.initialize(event.getCandidats());
			Hibernate.initialize(event.getJurys());
			List<Critere> criteres = event.getCriteres();
			for (Critere c : criteres) {
				Hibernate.initialize(c.getDescripteurs());
			}
			transaction.commit();
			logger.info("Find event done - Id: " + event.getIdevent());
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
		return event;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evenement> findByIdUser(int idUser) {
		if(idUser < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Evenement> events = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
			events = (List<Evenement>) session
					.createQuery("FROM Evenement WHERE (isdeleted = :isDeleted AND iduser = :iduser)")
					.setParameter("iduser", idUser).setParameter("isDeleted", false).list();
			transaction.commit();
			logger.info("Find event by id user done !");
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
		return events;
	}

	@Override
	public Evenement findByCode(String code) {
		Objects.requireNonNull(code);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Evenement event = null;
		try {
			transaction = session.beginTransaction();
			event = (Evenement) session.createQuery("FROM Evenement WHERE code = :code")
					.setParameter("code", code).getSingleResult();
			transaction.commit();
			logger.info("Find event by code done !");
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
		return event;
	}

	@Override
	public void editEvenement(Evenement event) {
		Objects.requireNonNull(event);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.update(event);
			transaction.commit();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Evenement> findAll() {
		List<Evenement> events = new ArrayList<>();		
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();	
			events = (List<Evenement>) session.createQuery("from Evenement").list();
			transaction.commit();
			logger.info("Find all events done !");
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
		return events;
	}
	
	public void supprimerEvent(int idevent) {
		if(idevent < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Evenement event = (Evenement) session.createQuery("from Evenement where idevent = :idevent").setParameter("idevent", idevent).getSingleResult();
			event.setIsdeleted(true);
			session.update(event);
			transaction.commit();
			logger.info("Delete event done (isDeleted = true) - Id: " + event.getIdevent());
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
