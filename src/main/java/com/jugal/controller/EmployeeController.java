package com.jugal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jugal.model.Employee;
import com.jugal.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/departments/{id}/employees")
    public ResponseEntity<List<Employee>>getEmployeesByDept(@PathVariable Long id){
        return ResponseEntity.ok(empService.getEmployeesByDept(id));
    }

}
