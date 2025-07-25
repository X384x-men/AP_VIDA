package com.sytecso.component.exceptions;

import com.sytecso.component.DTOFile;
import com.sytecso.config.logger.SytecsoLogger;

public abstract class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3149690298460490318L;

	private  String message;
	private  DTOFile dtoFileError;

	public BaseException(String msg) {
		this.message = msg;
		SytecsoLogger.info(msg);
	}
	public BaseException(DTOFile dtoError) {
		this.dtoFileError = dtoError;
	}


	public String getMessage() {
		return message;
	}

	public DTOFile getDTOFileError() {
		return dtoFileError;
	}

}
