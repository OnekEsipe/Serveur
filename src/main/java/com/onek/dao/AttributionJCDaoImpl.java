package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;

@Repository
public class AttributionJCDaoImpl implements AttributionJCDao, Serializable{
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Candidat> listCandidatsByEvent(int idevent) {
		List<Candidat> candidats = new ArrayList<>();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		candidats = (List<Candidat>) session.createQuery("from Candidat where evenement.idevent = :idevent").setParameter("idevent", idevent).list();

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

		jurys = (List<Jury>) session.createQuery("from Jury where evenement.idevent = :idevent").setParameter("idevent", idevent).list();
		for (Jury jury : jurys) {
			utilisateurs = (List<Utilisateur>) session.createQuery("from Utilisateur where iduser = :iduser").setParameter("iduser", jury.getUtilisateur().getIduser()).list();
		}
		session.getTransaction().commit();
		session.close();
		
		return utilisateurs;
	}
}
