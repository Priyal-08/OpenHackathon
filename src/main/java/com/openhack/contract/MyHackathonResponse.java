package com.openhack.contract;

import java.util.Date;

import org.springframework.stereotype.Component;

//Hackathon response class 
@Component
public class MyHackathonResponse {
	
    private long id;
	
	private String eventName;
	
	private Date startDate;
	
	private Date endDate;
	
	private String description;
	
	private int role;
	
	public MyHackathonResponse() {}
	
	public long getId() {
		return id;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public MyHackathonResponse(long id, String eventName, Date startDate, Date endDate, String description, int role) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.role = role;
	}

}
