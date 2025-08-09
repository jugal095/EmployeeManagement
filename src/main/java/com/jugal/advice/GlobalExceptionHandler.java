package com.jugal.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jugal.exception.CEOAlreadyExistsException;
import com.jugal.exception.CannotMoveEmployeeException;
import com.jugal.exception.DepartmentAlreadyExistException;
import com.jugal.exception.DepartmentNotFoundException;
import com.jugal.exception.EmployeeNotFoundException;
import com.jugal.exception.ManagerNotFoundException;
import com.jugal.exception.NewEmployeeException;

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
	
	@ExceptionHandler(CEOAlreadyExistsException.class)
	public ResponseEntity<String> handleCEOAlreadyExistsException(CEOAlreadyExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(NewEmployeeException.class)
	public ResponseEntity<String> handleNewEmployeeException(NewEmployeeException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CannotMoveEmployeeException.class)
	public ResponseEntity<String> handleCannotMoveEmployeeExceptionn(CannotMoveEmployeeException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
