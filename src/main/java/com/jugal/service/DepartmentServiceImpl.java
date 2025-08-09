package com.jugal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jugal.exception.DepartmentAlreadyExistException;
import com.jugal.model.Department;
import com.jugal.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository deptRepo;

	@Transactional
	public Department addDepartment(Department d) {
		if (deptRepo.findByName(d.getName()).isPresent()) {
	        throw new DepartmentAlreadyExistException("Department name already exists: " + d.getName());
	    }
		return deptRepo.save(d);
	}
}
