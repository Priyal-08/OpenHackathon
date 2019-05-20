package com.openhack.exception;

public class OperationNotAllowedException  extends Exception{
	
	private static final long serialVersionUID = 3515224144517952190L;

	public OperationNotAllowedException(String msg) {
		super(msg);
	}
}
