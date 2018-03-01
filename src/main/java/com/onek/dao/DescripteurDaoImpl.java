package com.onek.dao;

import java.io.Serializable;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Descripteur;

/**
 * Dao du model Descripteur
 */
@Repository
public class DescripteurDaoImpl implements DescripteurDao, Serializable{

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(DescripteurDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void supprimerDescripteur(Descripteur descripteur) {
		Objects.requireNonNull(descripteur);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(descripteur);
			transaction.commit();
			logger.info("Delete descripteur done !");	
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
