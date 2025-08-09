package com.jugal.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugal.exception.BusinessRuleException;
import com.jugal.model.Department;
import com.jugal.model.Employee;
import com.jugal.model.EmployeeSummary;
import com.jugal.repository.DepartmentRepository;
import com.jugal.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private  EmployeeRepository empRepo;
	
	@Autowired
    private DepartmentRepository deptRepo;

	public List<EmployeeSummary> getEmployeesByDept(Long deptId){
		 Department d = deptRepo.findById(deptId)
		            .orElseThrow(() -> new BusinessRuleException("Dept not found"));
		    return empRepo.findByDepartment(d)
		            .stream()
		            .map(e -> new EmployeeSummary(e.getId(), e.getName(), e.getEmail(), e.getTitle()))
		            .toList();
    }
}
