package com.openhack.contract;

import org.springframework.stereotype.Component;

@Component
public class UserResponse {
	
	private String email;
	
	private String status;

	
	public UserResponse() {}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserResponse(String email, String status) {
		super();
		this.email = email;
		this.status = status;
	}
	
	public UserResponse(String status) {
		super();
		this.status = status;
	}

}
