package com.openhack.exception;

public class DuplicateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2833399038121711829L;
	
	public DuplicateException(String message) {
		super(String.format("Entity with name %s already exist", message));
	}
	
	public DuplicateException(String entity, String paramName, String paramValue) {
		super(String.format("%s with %s \"%s\" already exist", entity, paramName, paramValue));
	}
}
