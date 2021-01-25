package com.cognizant.truyum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "UserId Mismatch with the login User")
public class UserIdMismatchException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
