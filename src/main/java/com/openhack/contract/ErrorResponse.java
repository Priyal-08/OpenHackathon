package com.openhack.contract;

import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {

	private String status;
	private String statusCode;
	private String message;
	
	public ErrorResponse() {}
	
	public ErrorResponse(String status, String statusCode, String message) {
		super();
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
