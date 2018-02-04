package com.onek.managedbean;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Employee;
import com.onek.service.EmployeeService;

@Component("empBean")
public class EmployeeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Employee employee = new Employee();
	
	@Autowired
	private EmployeeService empService;
	
	private Employee emp1;
	private Employee emp2;

	public EmployeeBean() {
		emp1 = new Employee();
		emp1.setFirstName("Onek");
		emp1.setLastName("User");
		
		emp2 = new Employee();
		emp2.setFirstName("Onek2");
		emp2.setLastName("User2");
	}

	public void addEmployee() {
		empService.addEmployee(emp1);
		empService.addEmployee(emp2);
		
		employee = empService.findEmployeeByFirstName("Onek2");
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
