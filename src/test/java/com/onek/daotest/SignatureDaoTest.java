package com.onek.daotest;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onek.dao.EvaluationDao;
import com.onek.dao.SignatureDao;
import com.onek.model.Signature;


@ContextConfiguration(locations = "classpath:application-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SignatureDaoTest {
	
	@Autowired
	private SignatureDao signatureDao;
	
	@Autowired
	private EvaluationDao evaluationDao;
	
	@Test
	@Rollback(true)
	public void testaddSignature() {
		Signature signature = new Signature();
		signature.setEvaluation(evaluationDao.findById(2));
		signature.setNom("test signature");
		signatureDao.addSignature(signature);
	}

}
