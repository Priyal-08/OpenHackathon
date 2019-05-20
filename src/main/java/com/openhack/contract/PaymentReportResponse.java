package com.openhack.contract;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PaymentReportResponse {
	
	private String eventName;	
	private Date startDate;
	private Date endDate;	
	private String description;
	private Map <String, String> paidTeams;
	private Map <String, String> unpaidTeams;
	private Map <String, String> paidParticipants;
	private Map <String, String> unpaidParticipants;
	
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

	public Map<String, String> getPaidTeams() {
		return paidTeams;
	}
	public void setPaidTeams(Map<String, String> paidTeams) {
		this.paidTeams = paidTeams;
	}
	public Map<String, String> getUnpaidTeams() {
		return unpaidTeams;
	}
	public void setUnpaidTeams(Map<String, String> unpaidTeams) {
		this.unpaidTeams = unpaidTeams;
	}
	public Map<String, String> getPaidParticipants() {
		return paidParticipants;
	}
	public void setPaidParticipants(Map<String, String> paidParticipants) {
		this.paidParticipants = paidParticipants;
	}
	public Map<String, String> getUnpaidParticipants() {
		return unpaidParticipants;
	}
	public void setUnpaidParticipants(Map<String, String> unpaidParticipants) {
		this.unpaidParticipants = unpaidParticipants;
	}
	public PaymentReportResponse(String eventName, Date startDate, Date endDate, String description,
			Map<String, String> paidTeams, Map<String, String> unpaidTeams,
			Map<String, String> paidParticipants, Map<String, String> unpaidParticipants) {
		super();
		this.eventName = eventName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.paidTeams = paidTeams;
		this.unpaidTeams = unpaidTeams;
		this.paidParticipants = paidParticipants;
		this.unpaidParticipants = unpaidParticipants;
	}


}
