package com.onek.dao;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Utilisateur;

@Repository
public class UserDaoImpl implements UserDao, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void updateUserInfos(Utilisateur user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Utilisateur userById(int iduser) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        Utilisateur user = (Utilisateur) session.createQuery("from Utilisateur where iduser = :iduser").setParameter("iduser", iduser).getSingleResult();
        session.getTransaction().commit();
        session.close();
		return user;
	}

}
