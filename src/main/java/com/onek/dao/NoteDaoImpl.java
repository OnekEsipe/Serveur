package com.onek.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Note;

/**
 * Dao du model Note
 */
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Note> getAllNotes() {
		List<Note> notes = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		transaction = session.beginTransaction();
		notes = (List<Note>) session.createQuery("from Note").list();
		transaction.commit();

		session.close();
		return notes;
	}

	@Override
	public Note findNoteById(int idnote) {
		if(idnote < 1) {
			throw new IllegalArgumentException("id must be positive"); 
		}
		Note note = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		note = (Note) session.createQuery("from Note where idnote = :idnote")
				.setParameter("idnote", idnote).getSingleResult();
		session.getTransaction().commit();
		session.close();
		return note;
	}


}
