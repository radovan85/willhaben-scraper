package com.radovan.spring.exceptions;

import javax.management.RuntimeErrorException;

public class InstanceUndefinedException extends RuntimeErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InstanceUndefinedException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
