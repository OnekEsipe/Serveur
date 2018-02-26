package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;

@Repository
public class CandidatesDaoImpl implements CandidatesDao, Serializable {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(CandidatesDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public  List<Candidat> findCandidatesByEvent(int idevent) {
		if(idevent < 1) {
			throw new IllegalArgumentException("idevent must be positive");
		}
		List<Candidat> candidates = new ArrayList<>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			candidates = (List<Candidat>) session.createQuery("from Candidat where evenement.idevent = :idevent order by nom").setParameter("idevent", idevent) .list();
			transaction.commit();
			logger.info("Find candidates by event done !");
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
		return candidates;
	}

	@Override
	public void addCandidate(Candidat candidat) {
		Objects.requireNonNull(candidat);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(candidat);
			transaction.commit();			
			logger.info("Add candidate done !");
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
	public void addCandidates(List<Candidat> candidates) {
		Objects.requireNonNull(candidates);
		if(candidates.isEmpty()) {
			throw new IllegalStateException("list must not be empty");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			candidates.forEach(candidat -> session.save(candidat));
			transaction.commit();
			logger.info("Add all candidates done !");	
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
	public void supprimerCandidat(int idcandidat) {
		if(idcandidat < 1) {
			throw new IllegalArgumentException("idcandidate must be positive");
		}
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Candidat candidatSupprime = session.get(Candidat.class, idcandidat);
			session.createQuery("delete from Candidat where idcandidat = :idcandidat")
					.setParameter("idcandidat", candidatSupprime.getIdcandidat()).executeUpdate();
			transaction.commit();
			logger.info("Delete candidate done !");
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
	public Candidat findCandidatesById(int idcandidat) {
		if(idcandidat < 1) {
			throw new IllegalArgumentException("idcandidate must be positive");
		}
		Candidat candidat = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			candidat = (Candidat) session.createQuery("from Candidat where idcandidat = :idcandidat").setParameter("idcandidat", idcandidat).getSingleResult();
			transaction.commit();
			logger.info("Find candidate by id done !");
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
		return candidat;
	}
}
