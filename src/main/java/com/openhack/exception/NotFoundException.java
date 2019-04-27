package com.openhack.exception;

public class NotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2734484419657603674L;
	
	public NotFoundException(String id) {
		super(String.format("Entity with given id- %s does not exist", id));
	}
	
	public NotFoundException(String entity, String paramName, long paramValue) {
		super(String.format("%s with given %s- %s does not exist",entity, paramName, paramValue));
	}

}
