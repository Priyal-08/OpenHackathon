package com.openhack.exception;

public class InvalidArgumentException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3397744267449845053L;

	public InvalidArgumentException(String message) {
		super(String.format("Argument %s is invalid", message));
	}
}
