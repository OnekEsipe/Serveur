package com.onek.beanstest;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.onek.managedbean.AccountBean;
import com.onek.managedbean.AccueilBean;
import com.onek.managedbean.AddJuryBean;
import com.onek.managedbean.AddUserBean;
import com.onek.managedbean.AttributionJCBean;
import com.onek.managedbean.CandidateBean;
import com.onek.managedbean.EventAccueilBean;
import com.onek.managedbean.EventBean;
import com.onek.managedbean.GrilleBean;
import com.onek.managedbean.LoginBean;
import com.onek.managedbean.MenuBean;
import com.onek.managedbean.ResetPasswordBean;
import com.onek.managedbean.StatistiquesBean;
import com.onek.managedbean.UsersBean;

public class SettersAndGettersTest {

	@Test
	public void validAccountBean() throws IntrospectionException {
		JavaBeanTester.test(AccountBean.class);
	}
	
	@Test
	public void validAccueilBean() throws IntrospectionException {
		JavaBeanTester.test(AccueilBean.class,"events","filteredevents");
	}
	
	@Test
	public void validAddJuryBean() throws IntrospectionException {
		JavaBeanTester.test(AddJuryBean.class,"utilisateurs","filteredutilisateurs","selectedutilisateurs","utilisateursAll","utilisateursAnos");
	}
	
	@Test
	public void validAttributionJCBean() throws IntrospectionException {
		//On ignore les tests sur les structures de type List, Map etc.
		JavaBeanTester.test(AttributionJCBean.class,"utilisateursJurys","candidatsJurys","jurys","candidats",
				"juryList","associatedJurysCandidates","attributionFinal","attribJC","messageAttrib");
	}
	
	@Test
	public void validCandidateBean() throws IntrospectionException {
		JavaBeanTester.test(CandidateBean.class,"filteredcandidats","candidats","importedCandidates");
	}
	
	@Test
	public void validEventAccueilBean() throws IntrospectionException {
		JavaBeanTester.test(EventAccueilBean.class,"filteredcandidats","candidats","utilisateurs","utilisateursAnos",
				"filteredutilisateurs","selectedoptions");
	}
	
	@Test
	public void validEventBean() throws IntrospectionException {
		JavaBeanTester.test(EventBean.class);
	}

	@Test
	public void validGrilleBean() throws IntrospectionException {
		JavaBeanTester.test(GrilleBean.class,"criteres","newCriteres","numbers","ref","coefficient","poids1",
				"poids2","poids3","poids4","poids5","poids6");
	}
	
	@Test
	public void validLoginBean() throws IntrospectionException {
		JavaBeanTester.test(LoginBean.class);
	}
	
	@Test
	public void validMenuBean() throws IntrospectionException {
		JavaBeanTester.test(MenuBean.class);
	}
	
	@Test
	public void validResetPasswordBean() throws IntrospectionException {
		JavaBeanTester.test(ResetPasswordBean.class);
	}

	@Test
	public void validStatistiquesBean() throws IntrospectionException {
		JavaBeanTester.test(StatistiquesBean.class,"notesByCandidats","notesByJurys","candidats","jurys","filteredNotesByCandidats","filteredNotesByJurys");
	}

	@Test
	public void validUsersBean() throws IntrospectionException {
		JavaBeanTester.test(UsersBean.class,"users","filteredusers","selectedusers");
	}
	
	@Test
	public void validAddUserBean() throws IntrospectionException {
		JavaBeanTester.test(AddUserBean.class, "users");
	}

}
