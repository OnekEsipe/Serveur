package com.onek.daotest;

import java.util.ArrayList;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.JuryDao;
import com.onek.model.Jury;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JuryDaoTest {

	@Autowired
	private JuryDao juryDao; 
	
	/*
	 * TESTS DES ARGUMENTS
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testFindAnonymousByIdEvent() {
		juryDao.findAnonymousByIdEvent(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFindById() {
		juryDao.findById(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindJurysAnnonymesByEvent() {
		juryDao.findJurysAnnonymesByEvent(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindJurysByIdevent() {
		juryDao.findJurysByIdevent(-1);
	}
	

	
	@Test(expected=IllegalArgumentException.class)
	public void testJuryIsAssigned1() {
		juryDao.juryIsAssigned(-1, 10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testJuryIsAssigned2() {
		juryDao.juryIsAssigned(10, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSupprimerUtilisateur1() {
		juryDao.supprimerUtilisateur(-1, 10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSupprimerUtilisateur2() {
		juryDao.supprimerUtilisateur(10, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testlistJurysByEvent() {
		juryDao.listJurysByEvent(-1);
	}
	

	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionFindByUser() {
		juryDao.findByUser(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionaddJuryToEvent() {
		juryDao.addJuryToEvent(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testfindJuryAndAnonymousByIdEvent1() {
		juryDao.findJuryAndAnonymousByIdEvent(-1, "login");
	}
	
	@Test(expected=NullPointerException.class)
	public void testfindJuryAndAnonymousByIdEvent2() {
		juryDao.findJuryAndAnonymousByIdEvent(10, null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testaddListJurys1() {
		List<Jury> jurys = new ArrayList<>();
		juryDao.addListJurys(jurys);
	}
	
	@Test(expected=NullPointerException.class)
	public void testaddListJurys2() {
		juryDao.addListJurys(null);
	}	
	
}
