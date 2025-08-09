package com.jugal.exception;

public class EmployeeNotFoundException extends RuntimeException {

	public EmployeeNotFoundException(String msg) {
		super(msg);
	}
}
