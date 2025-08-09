package com.jugal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jugal.model.Employee;
import com.jugal.model.EmployeeSummary;
import com.jugal.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/departments/{id}/employees")
    public ResponseEntity<List<EmployeeSummary>>getEmployeesByDept(@PathVariable Long id){
        return ResponseEntity.ok(empService.getEmployeesByDept(id));
    }
	
	@GetMapping("/managers/{id}/reports")
    public ResponseEntity<List<EmployeeSummary>> getReportsToManager(@PathVariable Long id){
        return ResponseEntity.ok(empService.getReportsToManager(id));
    }

}
