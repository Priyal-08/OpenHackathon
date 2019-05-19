package com.openhack.contract;

import org.springframework.stereotype.Component;

@Component
public class HackathonWiseRevenueResponse {
	
    private long id;
	
	private String eventName;
	
	private float amount;
	
	public HackathonWiseRevenueResponse() {}
	
	public HackathonWiseRevenueResponse(long id, String eventName, float amount) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

}
