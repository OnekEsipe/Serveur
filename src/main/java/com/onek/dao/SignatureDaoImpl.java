package com.onek.dao;

import java.io.Serializable;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Signature;

@Repository
public class SignatureDaoImpl implements SignatureDao, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(SignatureDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addSignature(Signature signature) {
		Objects.requireNonNull(signature);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(signature);
			transaction.commit();			
			logger.info("Add signature done !");
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
