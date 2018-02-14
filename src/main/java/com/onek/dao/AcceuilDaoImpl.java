package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Evenement;

@Repository
public class AcceuilDaoImpl implements AccueilDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Evenement> listEvents() {
		List<Evenement> events = new ArrayList<>();
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	//	boolean isdeleted = false;
	//	events = (List<Evenement>) session.createQuery("from Evenement where isdeleted = :isdeleted").setParameter("isdeleted", isdeleted).list();
		events = (List<Evenement>) session.createQuery("from Evenement").list();
		session.getTransaction().commit();
		session.close();
		return events;
	}
	@Override
	public void supprimerEvent(int idevent) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Evenement event = (Evenement) session.createQuery("from Evenement where idevent = :idevent").setParameter("idevent", idevent).getSingleResult();
		System.out.println(event.getIdevent());
		event.setIsdeleted(true);
		System.out.println(event.getNom());
		System.out.println(event.getIsdeleted());
		session.update(event);
		session.getTransaction().commit();
		session.close();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Evenement> myListEvents(int iduser) {
		List<Evenement> events = new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		boolean isdeleted = false;
		events = (List<Evenement>) session.createQuery("from Evenement where (isdeleted = :isdeleted and iduser = :iduser)").setParameter("isdeleted", isdeleted).setParameter("iduser", iduser).list();
		session.getTransaction().commit();
		session.close();
		return events;
	}

}
