package com.onek.daotest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.JuryDao;

@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JuryDaoTest {

	@Autowired
	private JuryDao juryDao; 
	
	/*
	 * TESTS DES ARGUMENTS
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId1() {
		juryDao.findAnonymousByIdEvent(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId2() {
		juryDao.findById(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId3() {
		juryDao.findJurysAnnonymesByEvent(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId4() {
		juryDao.findJurysByIdevent(-1);
	}
	

	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId5() {
		juryDao.juryIsAssigned(-1, 10);
		juryDao.juryIsAssigned(10, -1);
		juryDao.juryIsAssigned(-1, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId6() {
		juryDao.supprimerUtilisateur(-1, 10);
		juryDao.supprimerUtilisateur(10, -1);
		juryDao.supprimerUtilisateur(-1, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalId7() {
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
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionaddListJurys() {
		juryDao.addListJurys(null);
	}
	
	
	
	
	
	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 * TODO
	 */
	
	
	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * TODO
	 */

}
