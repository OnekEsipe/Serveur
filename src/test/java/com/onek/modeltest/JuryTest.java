package com.onek.modeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.model.Jury;

public class JuryTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getIdTest() {
		Jury jury = Mockito.mock(Jury.class);

		Mockito.when(jury.getIdjury()).thenReturn(1);

		assertEquals(1, jury.getIdjury().intValue());
	}
	
	@Test
	public void testEqualsValidate() {
		Jury jury1 = new Jury();
		jury1.setIdjury(1);
		Jury jury2 = new Jury();
		jury2.setIdjury(1);
		assertEquals(jury1, jury2);
	}
	
	@Test
	public void testEqualsFailure() {
		Jury jury1 = new Jury();
		jury1.setIdjury(1);
		Jury jury2 = new Jury();
		jury2.setIdjury(2);
		assertNotEquals(jury1, jury2);
	}
	
	@Test
	public void testHashcode() {
		Jury jury1 = new Jury();
		jury1.setIdjury(1);
		Jury jury2 = new Jury();
		jury2.setIdjury(2);
		assertNotEquals(jury1.hashCode(), jury2.hashCode());
	}
}
