package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		if(idevent < 1) {
			throw new IllegalArgumentException("idevent must be positive");
		}
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
		Objects.requireNonNull(candidat);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(candidat);
		session.getTransaction().commit();
		session.close();
		System.out.println("Add done !");
	}


	@Override
	public void addCandidates(List<Candidat> candidates) {
		Objects.requireNonNull(candidates);
		if(candidates.isEmpty()) {
			throw new IllegalArgumentException("list must not be empty");
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		candidates.forEach(candidat -> session.save(candidat));
		session.getTransaction().commit();
		session.close();
		System.out.println("Add All done !");
		
	}
		
	@Override
	public void supprimerCandidat(int idcandidat) {
		if(idcandidat < 1) {
			throw new IllegalArgumentException("idcandidate must be positive");
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Candidat candidatSupprime = session.get(Candidat.class, idcandidat);
		session.createQuery("delete from Candidat where idcandidat = :idcandidat")
				.setParameter("idcandidat", candidatSupprime.getIdcandidat()).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
}
