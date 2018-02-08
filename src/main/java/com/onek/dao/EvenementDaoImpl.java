package com.onek.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Critere;
import com.onek.model.Evenement;

@Repository
public class EvenementDaoImpl implements EvenementDao, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addEvenement(Evenement event) {
		System.out.println("DAO called - insert of : " + event.getNom());

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(event);
		session.getTransaction().commit();
		session.close();

		System.out.println("Add done");		
	}

	@Override
	public Evenement findById(int id) {
		Session session = sessionFactory.openSession();		
        session.beginTransaction();
        
		Evenement event = (Evenement) session.createQuery("FROM Evenement WHERE idevent = :id").setParameter("id", id)
				.getSingleResult();
        Hibernate.initialize(event.getCriteres());        
        
        List<Critere> criteres = event.getCriteres();
        for(Critere c : criteres) {
        	Hibernate.initialize(c.getDescripteurs());
        }
        
        session.getTransaction().commit();
        session.close();
        
        System.out.println("Find done - Id: " + event.getIdevent());
		return event;		
	}	

}
