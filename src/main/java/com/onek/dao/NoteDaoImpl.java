package com.onek.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Note;

@Repository
public class NoteDaoImpl implements NoteDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Note addNote(Note note) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(note);
		session.getTransaction().commit();
		session.close();
		return note;
	}

	@Override
	public void update(Note note) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(note);
		session.getTransaction().commit();
		session.close();
	}	
	
}
