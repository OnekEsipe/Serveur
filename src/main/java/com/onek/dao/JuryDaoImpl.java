package com.onek.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Critere;
import com.onek.model.Evaluation;
import com.onek.model.Jury;
import com.onek.model.Note;

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
	public List<Jury> findJuryAndAnonymousByIdEvent(int idEvent, String login) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
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
	public List<Jury> findAnonymousByIdEvent(int idEvent) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
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

}
