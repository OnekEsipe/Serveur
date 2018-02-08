package com.onek.springtest;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.onek.service.ApplicationService;

public class ApplicationServiceTest {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Test
	public void exportIdEventCheckFormatFailure() {
		assertEquals(Optional.empty(), applicationService.export("toto", "admin"));
	}

}
