package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Jury;
import com.onek.model.Utilisateur;

@Repository
public class AddJuryDaoImpl implements AddJuryDao, Serializable{
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> listJurysByEvent(int idevent){
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
	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> listJurysAnnonymesByEvent(int idevent){
		List<Utilisateur> utilisateurs = new ArrayList<>();
		List<Jury> jurys = new ArrayList<>();
		List<Utilisateur> utilisateursAnnonymes = new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		jurys = (List<Jury>) session.createQuery("from Jury where evenement.idevent = :idevent").setParameter("idevent", idevent).list();
		for (Jury jury : jurys) {
			utilisateurs = (List<Utilisateur>) session.createQuery("from Utilisateur where iduser = :iduser").setParameter("iduser", jury.getUtilisateur().getIduser()).list();
		}
		for (Utilisateur utilisateur : utilisateurs) {
			if(utilisateur.getIsanonym()) {
				utilisateursAnnonymes.add(utilisateur);
			}	
		}
		session.getTransaction().commit();
		session.close();
		
		return utilisateursAnnonymes;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> listJurysAll(){
		List<Utilisateur> utilisateurs = new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		utilisateurs = (List<Utilisateur>) session.createQuery("from Utilisateur").list();
		session.getTransaction().commit();
		session.close();
		
		return utilisateurs;
	}
}
