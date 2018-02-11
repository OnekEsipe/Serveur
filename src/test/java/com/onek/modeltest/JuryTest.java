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
}
