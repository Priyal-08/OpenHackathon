package com.openhack.contract;

public class Judge {
	
	public Judge(long userId, String name) {
		super();
		this.userId = userId;
		this.name = name;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private long userId;

	private String name;
}
