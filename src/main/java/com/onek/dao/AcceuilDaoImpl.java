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
		boolean isdeleted = false;
		events = (List<Evenement>) session.createQuery("from Evenement where isdeleted = :isdeleted")
				.setParameter("isdeleted", isdeleted).list();
		// events = (List<Evenement>) session.createQuery("from Evenement order by
		// nom").list();
		session.getTransaction().commit();
		session.close();
		return events;
	}

	@Override
	public void supprimerEvent(int idevent) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Evenement event = (Evenement) session.createQuery("from Evenement where idevent = :idevent")
				.setParameter("idevent", idevent).getSingleResult();
		event.setIsdeleted(true);
		session.update(event);
		session.getTransaction().commit();
		session.close();
	}

}
