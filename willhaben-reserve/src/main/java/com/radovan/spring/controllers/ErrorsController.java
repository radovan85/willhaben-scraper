package com.radovan.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.exceptions.TooManyRequestsException;

@ControllerAdvice
public class ErrorsController {

	@Autowired
	private TempConverter tempConverter;

	@ExceptionHandler(TooManyRequestsException.class)
	public ResponseEntity<String> handleTooManyRequestsException(Error error) {
		System.err.println("Overheatting: " + tempConverter.getCurrentAustriaTimestamp());
		return new ResponseEntity<>(error.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
	}
	
	
}
