package com.onek.modeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.onek.model.Utilisateur;

public class UtilisateurTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getIdTest() {
		Utilisateur utilisateur = Mockito.mock(Utilisateur.class);

		Mockito.when(utilisateur.getIduser()).thenReturn(1);

		assertEquals(1, utilisateur.getIduser().intValue());
	}

	@Test
	public void droitsTest() {
		Utilisateur utilisateur = Mockito.mock(Utilisateur.class);
		Mockito.when(utilisateur.getDroits()).thenCallRealMethod();

		assertEquals(null, utilisateur.getDroits());

		Mockito.doCallRealMethod().when(utilisateur).setDroits(Mockito.anyString());

		utilisateur.setDroits("A");
		assertEquals("A", utilisateur.getDroits());
	}

	@Test
	public void isanonymeTest() {
		Utilisateur utilisateur = Mockito.mock(Utilisateur.class);
		Mockito.when(utilisateur.getIsanonym()).thenCallRealMethod();

		assertEquals(null, utilisateur.getIsanonym());

		Mockito.doCallRealMethod().when(utilisateur).setIsanonym(Mockito.anyBoolean());

		utilisateur.setIsanonym(true);
		assertEquals(true , utilisateur.getIsanonym());
	}
	
	@Test
	public void loginTest() {
		Utilisateur utilisateur = Mockito.mock(Utilisateur.class);
		Mockito.when(utilisateur.getLogin()).thenCallRealMethod();

		assertEquals(null, utilisateur.getLogin());

		Mockito.doCallRealMethod().when(utilisateur).setLogin(Mockito.anyString());

		utilisateur.setLogin("toto12");
		assertEquals("toto12", utilisateur.getLogin());
	}
	
	@Test
	public void mailTest() {
		Utilisateur utilisateur = Mockito.mock(Utilisateur.class);
		Mockito.when(utilisateur.getMail()).thenCallRealMethod();

		assertEquals(null, utilisateur.getMail());

		Mockito.doCallRealMethod().when(utilisateur).setMail(Mockito.anyString());

		utilisateur.setMail("toto12@gmail.com");
		assertEquals("toto12@gmail.com", utilisateur.getMail());
	}
	
	@Test
	public void motdepasseTest() {
		Utilisateur utilisateur = Mockito.mock(Utilisateur.class);
		Mockito.when(utilisateur.getMotdepasse()).thenCallRealMethod();

		assertEquals(null, utilisateur.getMotdepasse());

		Mockito.doCallRealMethod().when(utilisateur).setMotdepasse(Mockito.anyString());

		utilisateur.setMotdepasse("toto12mdp");
		assertEquals("toto12mdp", utilisateur.getMotdepasse());
	}
	
	@Test
	public void nomprenomTest() {
		Utilisateur utilisateur = Mockito.mock(Utilisateur.class);
		Mockito.when(utilisateur.getNom()).thenCallRealMethod();
		Mockito.when(utilisateur.getPrenom()).thenCallRealMethod();

		assertEquals(null, utilisateur.getNom());
		assertEquals(null, utilisateur.getPrenom());

		Mockito.doCallRealMethod().when(utilisateur).setNom(Mockito.anyString());
		Mockito.doCallRealMethod().when(utilisateur).setPrenom(Mockito.anyString());

		utilisateur.setNom("totoo");
		utilisateur.setPrenom("tataa");
		assertEquals("totoo", utilisateur.getNom());
		assertEquals("tataa", utilisateur.getPrenom());
	}
}
