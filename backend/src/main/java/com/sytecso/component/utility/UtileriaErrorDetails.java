package com.sytecso.component.utility;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sytecso.component.utility.UtileriaFechas;
import com.sytecso.dto.errors.ErrorDetails;
import com.sytecso.component.exceptions.SytecsoController;

public class UtileriaErrorDetails {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private UtileriaErrorDetails() {
		throw new IllegalStateException("this class cannot be instanced");
	}

	public static ResponseEntity<ErrorDetails> sendError(String details, Exception e, HttpStatus status) {
		SytecsoController.logClassAndMethodWithException(e);
		return new ResponseEntity<>(
				new ErrorDetails(UtileriaFechas.generateDate(DATE_FORMAT), details, e.getMessage()), status);
	}
	public static ResponseEntity<ErrorDetails> sendError(String details, Exception e, HttpStatus status,String errorCustomMessage) {
		SytecsoController.logClassAndMethodWithException(e);
		return new ResponseEntity<>(
				new ErrorDetails(UtileriaFechas.generateDate(DATE_FORMAT), details, errorCustomMessage), status);
	}
}
