package com.onek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Evaluation;
import com.onek.model.Jury;
import com.onek.model.Note;
import com.onek.model.Utilisateur;
import com.onek.utils.DroitsUtilisateur;

@Repository
public class JuryDaoImpl implements JuryDao, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(JuryDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean juryIsAssigned(int idUser, int idEvent) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Long result = -1L;
		try {
			transaction = session.beginTransaction();
			result = (Long) session.createQuery("SELECT COUNT(j) FROM Jury j WHERE (iduser = :idu AND idevent = :ide)")
					.setParameter("idu", idUser).setParameter("ide", idEvent).getSingleResult();
			transaction.commit();
			logger.info("Check if jury is assigned done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return result > 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Jury> findJuryAndAnonymousByIdEvent(int idEvent, String login) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Jury> jurys = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
			jurys = (List<Jury>) session.createQuery(
					"SELECT DISTINCT j FROM Jury j, Utilisateur u WHERE idevent = :idEvent AND j.utilisateur = u AND (u.login = :login OR u.droits = 'A') AND u.isdeleted IS FALSE")
					.setParameter("idEvent", idEvent).setParameter("login", login).list();
			for (Jury jury : jurys) {
				Hibernate.initialize(jury.getUtilisateur());
				Hibernate.initialize(jury.getEvaluations());
				List<Evaluation> evaluations = jury.getEvaluations();
				for (Evaluation evaluation : evaluations) {
					Hibernate.initialize(evaluation.getNotes());
					List<Note> notes = evaluation.getNotes();
					for (Note note : notes) {
						Critere critere = note.getCritere();
						Hibernate.initialize(critere.getDescripteurs());
					}
				}
			}
			transaction.commit();
			logger.info("Find jury and anonymous by id event done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return jurys;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Jury> findAnonymousByIdEvent(int idEvent) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Jury> jurys = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
			jurys = (List<Jury>) session.createQuery(
					"SELECT DISTINCT j FROM Jury j, Utilisateur u WHERE idevent = :idEvent AND j.utilisateur = u AND u.droits = :droits AND u.isdeleted IS FALSE")
					.setParameter("idEvent", idEvent).setParameter("droits", DroitsUtilisateur.ANONYME.toString())
					.list();
			for (Jury jury : jurys) {
				Hibernate.initialize(jury.getUtilisateur());
			}
			transaction.commit();
			logger.info("Find anonymous by id event done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return jurys;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Jury> findByUser(Utilisateur user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Jury> jurys = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
			jurys = (List<Jury>) session.createQuery(
					"SELECT DISTINCT j FROM Jury j, Utilisateur u WHERE j.utilisateur = :user AND u.isdeleted IS FALSE")
					.setParameter("user", user).list();
			for (Jury jury : jurys) {
				Hibernate.initialize(jury.getEvenement());
			}
			transaction.commit();
			logger.info("Find jury by user done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return jurys;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Jury> findJurysByIdevent(int idevent) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Jury> jurys = new ArrayList<>();
		try {
			transaction = session.beginTransaction();
			jurys = (List<Jury>) session.createQuery("from Jury where evenement.idevent = :idevent")
					.setParameter("idevent", idevent).list();
			for (Jury jury : jurys) {
				Hibernate.initialize(jury.getEvaluations());
			}
			transaction.commit();
			logger.info("Find jury by id event done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return jurys;
	}

	/* associated jurys and candidates for an event */
	@Override
	public HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent) {
		HashMap<Jury, List<Candidat>> map = new HashMap<>();
		for (Jury jury : jurys) {
			if (!map.containsKey(jury)) {
				map.put(jury, new ArrayList<>());
			}
			List<Evaluation> evaluations = jury.getEvaluations();
			for (Evaluation evaluation : evaluations) {
				List<Candidat> candidates = map.get(jury);
				if (evaluation.getCandidat().getEvenement().getIdevent() == idevent) {
					candidates.add(evaluation.getCandidat());
				}
				map.put(jury, candidates);
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> listJurysByEvent(int idevent) {
		List<Utilisateur> utilisateurs = new ArrayList<>();
		List<Jury> jurys = new ArrayList<>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			jurys = (List<Jury>) session.createQuery("from Jury where evenement.idevent = :idevent")
					.setParameter("idevent", idevent).list();
			for (Jury jury : jurys) {
				Utilisateur u = (Utilisateur) session.createQuery("from Utilisateur where iduser = :iduser")
						.setParameter("iduser", jury.getUtilisateur().getIduser()).getSingleResult();
				utilisateurs.add(u);
			}
			transaction.commit();
			logger.info("Find jury by id event done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return utilisateurs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> findJurysAnnonymesByEvent(int idevent) {
		List<Utilisateur> utilisateurs = new ArrayList<>();
		List<Jury> jurys = new ArrayList<>();
		List<Utilisateur> utilisateursAnnonymes = new ArrayList<>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			jurys = (List<Jury>) session.createQuery("from Jury where evenement.idevent = :idevent")
					.setParameter("idevent", idevent).list();
			for (Jury jury : jurys) {
				utilisateurs = (List<Utilisateur>) session.createQuery("from Utilisateur where iduser = :iduser")
						.setParameter("iduser", jury.getUtilisateur().getIduser()).list();
			}
			for (Utilisateur utilisateur : utilisateurs) {
				if ((utilisateur.getDroits().equals(DroitsUtilisateur.ANONYME.toString()))
						&& (utilisateur.getIsdeleted() == false)) {
					utilisateursAnnonymes.add(utilisateur);
				}
			}
			transaction.commit();
			logger.info("Find jurys and anonymous by id event done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
		return utilisateursAnnonymes;
	}

	@Override
	public void supprimerUtilisateur(int iduser, int idevent) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();		
		session.createQuery("delete from Jury where iduser = :iduser AND idevent = :idevent")
				.setParameter("iduser", iduser).setParameter("idevent", idevent).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void addJuryToEvent(Jury jury) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(jury);
			transaction.commit();
			logger.info("Add jury done !");
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(this.getClass().getName(), e);
		} finally {
			session.close();
		}
	}

	@Override
	public void addListJurys(List<Jury> jurys) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			jurys.forEach(jury -> session.save(jury));
			transaction.commit();
			logger.info("Add all jurys done !");
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
	@Override
	public Utilisateur findById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilisateur jury = (Utilisateur) session.createQuery("FROM Utilisateur WHERE iduser = :id").setParameter("id", id)
				.getSingleResult();
		session.getTransaction().commit();
		session.close();
		return jury;
	}
}
