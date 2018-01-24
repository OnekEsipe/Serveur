package com.onek.model;

import java.io.Serializable;
import java.util.List;

public class Department  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long deptId;
	private String depName;
	private List<Employee> employees;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
