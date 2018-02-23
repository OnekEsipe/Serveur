package com.onek.daotest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.NoteDao;


@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NoteDaoTest {
	
	@Autowired
	private NoteDao noteDao;
	
	

	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionAddNote() {
		noteDao.addNote(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionUpdate() {
		noteDao.update(null);
	}
	
	/*
	 * TESTS DES ARGUMENTS
	 */
	
	/*
	 * TESTS DE TRANSACTIONS ECHOUEES
	 * TODO
	 */
	
	
	/*
	 * TESTS DE TRANSACTIONS REUSSIES
	 * TODO
	 */

}
