package com.jugal.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jugal.exception.DepartmentAlreadyExistException;
import com.jugal.exception.DepartmentNotFoundException;
import com.jugal.exception.ManagerNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<String> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ManagerNotFoundException.class)
	public ResponseEntity<String> handleManagerNotFoundException(ManagerNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DepartmentAlreadyExistException.class)
	public ResponseEntity<String> handleDepartmentAlreadyExistException(DepartmentAlreadyExistException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
}
