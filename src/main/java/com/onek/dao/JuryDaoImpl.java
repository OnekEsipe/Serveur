package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Evaluation;
import com.onek.model.Jury;
import com.onek.model.Note;
import com.onek.model.Utilisateur;

@Repository
public class JuryDaoImpl implements JuryDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean juryIsAssigned(int idUser, int idEvent) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		long result = (Long) session.createQuery("SELECT COUNT(j) FROM Jury j WHERE (iduser = :idu AND idevent = :ide)")
				.setParameter("idu", idUser).setParameter("ide", idEvent).getSingleResult();
		session.getTransaction().commit();
		session.close();
		return result > 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Jury> findJuryAndAnonymousByIdEvent(int idEvent, String login) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Jury> jurys = (List<Jury>) session.createQuery(
				"SELECT DISTINCT j FROM Jury j, Utilisateur u WHERE idevent = :idEvent AND j.utilisateur = u AND (u.login = :login OR u.droits = 'A') AND u.isdeleted IS FALSE")
				.setParameter("idEvent", idEvent).setParameter("login", login).list();
		for (Jury jury : jurys) {
			Hibernate.initialize(jury.getUtilisateur());
			Hibernate.initialize(jury.getEvaluations());
			List<Evaluation> evaluations = jury.getEvaluations();
			for (Evaluation evaluation : evaluations) {
				Hibernate.initialize(evaluation.getNotes());
				List<Note> notes = evaluation.getNotes();
				for (Note note : notes) {
					Critere critere = note.getCritere();
					Hibernate.initialize(critere.getDescripteurs());
				}
			}
		}
		session.getTransaction().commit();
		session.close();
		return jurys;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Jury> findAnonymousByIdEvent(int idEvent) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Jury> jurys = (List<Jury>) session.createQuery(
				"SELECT DISTINCT j FROM Jury j, Utilisateur u WHERE idevent = :idEvent AND j.utilisateur = u AND u.droits = 'A' AND u.isdeleted IS FALSE")
				.setParameter("idEvent", idEvent).list();
		for (Jury jury : jurys) {
			Hibernate.initialize(jury.getUtilisateur());
		}
		session.getTransaction().commit();
		session.close();
		return jurys;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Jury> findByUser(Utilisateur user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Jury> jurys = (List<Jury>) session.createQuery("SELECT DISTINCT j FROM Jury j, Utilisateur u WHERE j.utilisateur = :user AND u.isdeleted IS FALSE").setParameter("user", user).list();
		for(Jury jury : jurys) {
			Hibernate.initialize(jury.getEvenement());
		}
		session.getTransaction().commit();
		session.close();	
		return jurys;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jury> findJuryByIdevent(int idevent) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Jury> jurys = (List<Jury>) session.createQuery("from Jury where evenement.idevent = :idevent").setParameter("idevent", idevent) .list();
		for (Jury jury : jurys) {
			Hibernate.initialize(jury.getEvaluations());
		}
		session.getTransaction().commit();
		session.close();	
		return jurys;
	}
	
	/* associated jurys and candidates for an event */
	@Override
	public HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent) {
		HashMap<Jury, List<Candidat>> map = new HashMap<>();		
		for(Jury jury : jurys) {			
			if (!map.containsKey(jury)) {
				map.put(jury, new ArrayList<>());
			}			
			List<Evaluation> evaluations = jury.getEvaluations();
			for (Evaluation evaluation : evaluations) {
				List<Candidat> candidates = map.get(jury);
				if(evaluation.getCandidat().getEvenement().getIdevent() == idevent) {
					candidates.add(evaluation.getCandidat());
				}
				map.put(jury, candidates);
			}			
		}
		return map;
	}

}
