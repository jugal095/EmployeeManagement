package com.jugal.service;

import java.util.List;

import com.jugal.model.Employee;
import com.jugal.model.EmployeeSummary;

public interface EmployeeService {
	
	public List<EmployeeSummary> getEmployeesByDept(Long deptId);
	
	public List<EmployeeSummary> getReportsToManager(Long managerId);
	
	public Employee addEmployee(Employee e);

}
