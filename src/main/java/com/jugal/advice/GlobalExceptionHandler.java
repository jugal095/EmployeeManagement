package com.jugal.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jugal.exception.DepartmentNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(DepartmentNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
