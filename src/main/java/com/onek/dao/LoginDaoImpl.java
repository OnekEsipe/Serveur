package com.onek.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Utilisateur;

@Repository
public class LoginDaoImpl implements LoginDao, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Utilisateur findUserByLogin(String login) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        Utilisateur user = (Utilisateur) session.createQuery("from Utilisateur where login = :login").setParameter("login", login).getSingleResult();
        session.getTransaction().commit();
        session.close();
      
		return user;
	}
	
	@Override
	public boolean userExist(String login) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (Long) session.createQuery("SELECT COUNT(e) from Utilisateur e WHERE e.login =:login").setParameter("login", login).getSingleResult();
        session.getTransaction().commit();
        session.close();
        
        return result==1;
	}

}
