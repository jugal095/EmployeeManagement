package com.jugal.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugal.repository.DepartmentRepository;
import com.jugal.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private  EmployeeRepository empRepo;
	
	@Autowired
    private DepartmentRepository deptRepo;

    
}
