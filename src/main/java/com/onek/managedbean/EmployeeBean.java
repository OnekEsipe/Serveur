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

	public Employee getEmployee() {
		employee = empService.findEmployeeById(1L);
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
