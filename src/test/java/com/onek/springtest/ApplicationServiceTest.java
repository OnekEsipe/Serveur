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
		List<Integer> list = applicationService.parser("[1,2]");
		assertEquals(1, (int) list.get(0));
		assertEquals(2, (int) list.get(1));
	}	
	
	@Test(expected = IllegalArgumentException.class)	
	public void failParsingWithBadSyntax() {
		applicationService.parser("1,2]");
	}
	
	@Test(expected = IllegalArgumentException.class)	
	public void failParsingWithBadSyntax2() {
		applicationService.parser("[1,toto]");
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void failParsingWithNullValue() {
		applicationService.parser(null);
	}	

}
