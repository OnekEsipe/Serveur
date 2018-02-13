package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;

@Repository
public class EventAccueilDaoImpl implements EventAccueilDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Candidat> listCandidatsByEvent(int idevent) {
		List<Candidat> candidats = new ArrayList<>();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		candidats = (List<Candidat>) session.createQuery("from Candidat where evenement.idevent = :idevent")
				.setParameter("idevent", idevent).list();

		session.getTransaction().commit();
		session.close();

		return candidats;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> listJurysByEvent(int idevent) {
		List<Utilisateur> utilisateurs = new ArrayList<>();
		List<Jury> jurys = new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		jurys = (List<Jury>) session.createQuery("from Jury where evenement.idevent = :idevent")
				.setParameter("idevent", idevent).list();
		for (Jury jury : jurys) {
			Utilisateur u = (Utilisateur)session.createQuery("from Utilisateur where iduser = :iduser")
					.setParameter("iduser", jury.getUtilisateur().getIduser()).getSingleResult();
			utilisateurs.add(u);
			
		}
		session.getTransaction().commit();
		session.close();

		return utilisateurs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent) {
		List<Utilisateur> utilisateurs = new ArrayList<>();
		List<Jury> jurys = new ArrayList<>();
		List<Utilisateur> utilisateursAnnonymes = new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		jurys = (List<Jury>) session.createQuery("from Jury where evenement.idevent = :idevent")
				.setParameter("idevent", idevent).list();
		for (Jury jury : jurys) {
			utilisateurs = (List<Utilisateur>) session.createQuery("from Utilisateur where iduser = :iduser")
					.setParameter("iduser", jury.getUtilisateur().getIduser()).list();
		}
		for (Utilisateur utilisateur : utilisateurs) {
			if ((utilisateur.getDroits().equals("A")) && (utilisateur.getIsdeleted() == false)) {
				utilisateursAnnonymes.add(utilisateur);
			}
		}
		session.getTransaction().commit();
		session.close();

		return utilisateursAnnonymes;
	}

	@Override
	public void supprimerCandidat(int idcandidat) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Candidat candidatSupprime = session.get(Candidat.class, idcandidat);
		session.createQuery("delete from Candidat where idcandidat = :idcandidat")
				.setParameter("idcandidat", candidatSupprime.getIdcandidat()).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void supprimerUtilisateur(int iduser) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilisateur utilisateurSupprime = session.get(Utilisateur.class, iduser);
		session.createQuery("delete from Jury where iduser = :iduser")
				.setParameter("iduser", utilisateurSupprime.getIduser()).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	@Override
	public void editEvenement(Evenement event) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(event);
		session.getTransaction().commit();
		session.close();

		System.out.println("Modification done");	
	}

}
