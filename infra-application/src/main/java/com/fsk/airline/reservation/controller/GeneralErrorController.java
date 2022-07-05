package com.fsk.airline.reservation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GeneralErrorController {

	private final Logger logger = LoggerFactory.getLogger(GeneralErrorController.class);

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorDto> handleException(IllegalArgumentException ex, WebRequest webRequest) {
		logger.error("Error in request " + webRequest.getDescription(false), ex);
		ErrorDto errorDto = new ErrorDto(ex.getMessage());
		return new ResponseEntity<>(errorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}
