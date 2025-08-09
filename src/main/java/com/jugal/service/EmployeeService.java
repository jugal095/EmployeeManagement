package com.jugal.service;

import java.util.List;

import com.jugal.model.Employee;

public interface EmployeeService {
	
	public List<Employee> getEmployeesByDept(Long deptId);

}
