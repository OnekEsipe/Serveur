package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Evenement;
import com.onek.model.Jury;
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
	public Utilisateur findByLogin(String login) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilisateur user = (Utilisateur) session.createQuery("from Utilisateur where login = :login")
				.setParameter("login", login).getSingleResult();
		session.getTransaction().commit();
		session.close();
		return user;
	}

	@Override
	public void addJurysAnonymes(List<Utilisateur> utilisateurs, Evenement event) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (Utilisateur utilisateur : utilisateurs) {
			session.save(utilisateur);
			Jury jury = new Jury();
			jury.setEvenement(event);
			jury.setUtilisateur(utilisateur);
			session.save(jury);
		}
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> getAllUsers() {
		List<Utilisateur> users = new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		users = (List<Utilisateur>) session.createQuery("from Utilisateur").list();
		session.getTransaction().commit();
		session.close();
		return users;
	}

	@Override
	public void deleteUser(int idUser) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilisateur utilisateur = (Utilisateur) session.createQuery("from Utilisateur where iduser = :iduser")
				.setParameter("iduser", idUser).getSingleResult();
		utilisateur.setIsdeleted(true);
		session.update(utilisateur);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void addUser(Utilisateur user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> getAllUsersExceptCurrent(int idcurrentUser) {
		List<Utilisateur> users = new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		users = (List<Utilisateur>) session.createQuery("from Utilisateur where iduser != :iduser")
				.setParameter("iduser", idcurrentUser).list();
		session.getTransaction().commit();
		session.close();
		return users;
	}
	
	@Override
	public boolean mailExist(String mail) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		long result = (Long) session.createQuery("SELECT COUNT(e) from Utilisateur e WHERE e.mail = :mail")
				.setParameter("mail", mail).getSingleResult();
		session.getTransaction().commit();
		session.close();
		return result == 1;		
	}

	@Override
	public boolean userExist(String login) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		long result = (Long) session.createQuery("SELECT COUNT(e) from Utilisateur e WHERE e.login =:login")
				.setParameter("login", login).getSingleResult();
		session.getTransaction().commit();
		session.close();
		return result == 1;
	}
	
	@Override
	public Utilisateur findByMail(String mail) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilisateur user = (Utilisateur) session.createQuery("from Utilisateur where mail = :mail")
				.setParameter("mail", mail).getSingleResult();
		session.getTransaction().commit();
		session.close();
		return user;
	}
	@Override
	public Utilisateur findUserById(int iduser) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilisateur user = (Utilisateur) session.createQuery("from Utilisateur where iduser = :iduser")
				.setParameter("iduser", iduser).getSingleResult();
		session.getTransaction().commit();
		session.close();
		return user;
	}
	@Override
	public void EditUser(Utilisateur user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}
}
