package com.numpyninja.lms.exception;

public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdNotFoundException(String msg) {
		super(msg);
	}

}
