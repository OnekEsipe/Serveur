package com.onek.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Critere;

@Repository
public class GrilleDaoImpl implements GrilleDao, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addCriteres(List<Critere> criteres) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (Critere critere : criteres) {
			session.save(critere);
		}
		session.getTransaction().commit();
		session.close();
	}	

}