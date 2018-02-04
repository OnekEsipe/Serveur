package com.onek.dao;

import com.onek.model.Employee;

public interface EmployeeDao {
	
	public void addEmployee(Employee emp);
	public Employee findEmployeeByFirstName(String firstName);
}
