package com.onek.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public Evenement findByName(String name) {
		Evenement result = new Evenement();
		
		Session session = sessionFactory.openSession();
		
        session.beginTransaction();
        
        result = (Evenement) session.createQuery("FROM Evenement WHERE nom = :name").setParameter("name", name).getSingleResult();
        
        session.getTransaction().commit();
        session.close();
        
        System.out.println("Find done - Name: " + result.getNom());
		return result;		
	}	

}
