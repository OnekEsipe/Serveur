package com.onek.dao;

import java.io.Serializable;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Note;

@Repository
public class NoteDaoImpl implements NoteDao, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(NoteDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Note addNote(Note note) {
		Objects.requireNonNull(note);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(note);
			transaction.commit();
			logger.info("Add mark done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		}
		finally {
			session.close();
		}
		return note;
	}

	@Override
	public void update(Note note) {
		Objects.requireNonNull(note);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(note);
			transaction.commit();
			logger.info("Update mark done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		}
		finally {
			session.close();
		}
	}	
	
}
