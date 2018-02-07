package com.onek.springtest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.onek.service.ApplicationService;

public class ApplicationServiceTest {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Test
	public void successParsing() {
		List<String> list = applicationService.parser("[nom1,nom2]");
		assertEquals(list.get(0), "nom1");
		assertEquals(list.get(1), "nom2");
	}	
	
	@Test(expected = IllegalArgumentException.class)	
	public void failParsingWithBadSyntax() {
		applicationService.parser("nom1,nom2]");
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void failParsingWithNullValue() {
		applicationService.parser(null);
	}	

}
