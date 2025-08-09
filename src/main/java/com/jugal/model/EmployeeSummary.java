package com.jugal.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeSummary {
	private Long id;
	private String name;
	private String email;
	private String title;
}
