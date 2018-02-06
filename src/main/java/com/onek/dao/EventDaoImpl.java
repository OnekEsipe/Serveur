package com.onek.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EventDaoImpl implements EventDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addEvent(Event event) {
		System.out.println("DAO called - insert of : " + event.getFirstName());

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(event);
		session.getTransaction().commit();
		session.close();

		System.out.println("Add done");		
	}

}
