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
	public boolean validPassword(int iduser, String password) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (Long) session.createQuery("SELECT COUNT(e) from Utilisateur e WHERE (e.iduser =:iduser AND e.motdepasse =:motdepasse")
        		.setParameter("iduser", iduser).setParameter("motdepasse", password).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return result==1;
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
