package com.jugal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jugal.model.Department;
import com.jugal.model.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(Department dept);
    List<Employee> findByManager(Employee manager);
}

