package com.onek.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Critere;
import com.onek.model.Descripteur;

@Repository
public class CritereDaoImpl implements CritereDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addCriteres(List<Critere> criteres) {
		Objects.requireNonNull(criteres);
		if(criteres.isEmpty()) {
			throw new IllegalArgumentException("list must not be empty");
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (Critere critere : criteres) {
			session.save(critere);
			for (Descripteur descripteur : critere.getDescripteurs()) {
				session.save(descripteur);
			}
		}
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Critere findById(Integer id) {
		if(id < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Critere critere = (Critere) session.createQuery("FROM Critere WHERE idcritere = :id").setParameter("id", id)
				.getSingleResult();
		Hibernate.initialize(critere.getDescripteurs());
		session.getTransaction().commit();
		session.close();
		return critere;
	}

	@Override
	public void addCritere(Critere critere) {
		Objects.requireNonNull(critere);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(critere);
		for (Descripteur descripteur : critere.getDescripteurs()) {
			session.save(descripteur);
		}
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void supprimerCritere(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("id must be positive");
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Critere critere = findById(id);
		System.out.println(critere.getTexte()+" "+critere.getIdcritere());
		System.out.println(critere.getDescripteurs().size());
		for (Descripteur descripteur : critere.getDescripteurs()) {
			System.out.println("Descripteur : "+descripteur.getIddescripteur());
			session.createQuery("delete from Descripteur where iddescripteur = :iddescripteur")
			.setParameter("iddescripteur", descripteur.getIddescripteur()).executeUpdate();
		}
		session.createQuery("delete from Critere where idcritere = :idcritere")
		.setParameter("idcritere", id).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
}
