package com.onek.springtest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.onek.model.Employee;

public class EmployeeTest {

	@Autowired
	private Employee employee;

	@Test
	  public void testSomeMethod() {
	    System.out.println("Tests have been found");
	    assertEquals("Super", "Super");
	  }
	
	@Test
	public void employeeTest() {
		employee = new Employee();
		employee.setFirstName("Super");
		employee.setLastName("Batman");
		
		assertEquals("Super", employee.getFirstName());
		assertEquals("Batman", employee.getLastName());
	}

}
