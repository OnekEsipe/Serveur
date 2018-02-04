package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.EmployeeDao;
import com.onek.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService, Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	private EmployeeDao empDao;

	@Override
	public Employee findEmployeeByFirstName(String firstName) {
		return empDao.findEmployeeByFirstName(firstName);
	}

	@Override
	public void addEmployee(Employee emp) {
		empDao.addEmployee(emp);
	}
}