package com.radovan.spring.exceptions;

import javax.management.RuntimeErrorException;

public class TooManyRequestsException extends RuntimeErrorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooManyRequestsException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
}
