package com.openhack.contract;

import org.springframework.stereotype.Component;

@Component
public class UserResponse {
	
	private String email;
	
	public UserResponse() {}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserResponse(String email) {
		super();
		this.email = email;
	}

}
