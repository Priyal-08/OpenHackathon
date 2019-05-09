package com.openhack.contract;

public class ParticipantDetail {
	private long id;
	private String role;
	public ParticipantDetail(long id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
