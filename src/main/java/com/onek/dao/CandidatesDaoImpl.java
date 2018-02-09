package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;

@Repository
public class CandidatesDaoImpl implements CandidatesDao, Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public  List<Candidat> findCandidatesByEvent(int idevent) {

		List<Candidat> candidates = new ArrayList<>();

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		candidates = (List<Candidat>) session.createQuery("from Candidat where evenement.idevent = :idevent order by nom").setParameter("idevent", idevent) .list();

		session.getTransaction().commit();
		session.close();
		return candidates;
	}

	@Override
	public void addCandidate(Candidat candidat) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(candidat);
		session.getTransaction().commit();
		session.close();

		System.out.println("Add done");


	}
	
}
