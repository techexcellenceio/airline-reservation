package com.fsk.airline.reservation.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralErrorController {

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorDto> handleException(IllegalArgumentException ex) {
		ErrorDto errorDto = new ErrorDto(ex.getMessage());
		return new ResponseEntity<>(errorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}
