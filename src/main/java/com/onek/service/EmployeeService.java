package com.onek.service;

import com.onek.model.Employee;

public interface EmployeeService {
	public Employee findEmployeeByFirstName(String firstName);
	public void addEmployee(Employee emp);
}
