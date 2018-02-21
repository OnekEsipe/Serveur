package com.onek.daotest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import com.onek.dao.CandidatesDaoImpl;
public class CandidateDaoTest {
	
	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@Transactional
	public void testMethodFindCandidatesByEvent() {
		CandidatesDaoImpl candidat = Mockito.mock(CandidatesDaoImpl.class);		 
		 
	}

}
