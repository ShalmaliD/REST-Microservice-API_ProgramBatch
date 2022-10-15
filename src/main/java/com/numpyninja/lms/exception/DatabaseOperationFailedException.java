package com.numpyninja.lms.exception;

public class DatabaseOperationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseOperationFailedException(String msg) {
		super(msg);
	}
	
}
