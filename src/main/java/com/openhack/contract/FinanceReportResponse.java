package com.openhack.contract;

import java.util.Date;

public class FinanceReportResponse {

	private String eventName;	
	private Date startDate;
	private Date endDate;	
	private String description;
	private int noOfTeams; 
	private int noOfSponsors;
	private int totalParticipants; 
	private float hackathonFees; 
	private float totalHackathonFeesPaid; 
	private float totalHackathonFeesNotPaid; 
	private float avgFeesPaid; 
	private float revenue; 
	private float expense; 
	private float profit;
	
	public FinanceReportResponse(String eventName, Date startDate, Date endDate, String description, int noOfTeams,
			int noOfSponsors, int totalParticipants, float hackathonFees, float totalHackathonFeesPaid,
			float totalHackathonFeesNotPaid, float avgFeesPaid, float revenue, float expense, float profit) {
		super();
		this.eventName = eventName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.noOfTeams = noOfTeams;
		this.noOfSponsors = noOfSponsors;
		this.totalParticipants = totalParticipants;
		this.hackathonFees = hackathonFees;
		this.totalHackathonFeesPaid = totalHackathonFeesPaid;
		this.totalHackathonFeesNotPaid = totalHackathonFeesNotPaid;
		this.avgFeesPaid = avgFeesPaid;
		this.revenue = revenue;
		this.expense = expense;
		this.profit = profit;
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
	public int getNoOfTeams() {
		return noOfTeams;
	}
	public void setNoOfTeams(int noOfTeams) {
		this.noOfTeams = noOfTeams;
	}
	public int getNoOfSponsors() {
		return noOfSponsors;
	}
	public void setNoOfSponsors(int noOfSponsors) {
		this.noOfSponsors = noOfSponsors;
	}
	public int getTotalParticipants() {
		return totalParticipants;
	}
	public void setTotalParticipants(int totalParticipants) {
		this.totalParticipants = totalParticipants;
	}
	public float getHackathonFees() {
		return hackathonFees;
	}
	public void setHackathonFees(float hackathonFees) {
		this.hackathonFees = hackathonFees;
	}
	public float getRevenue() {
		return revenue;
	}
	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}
	public float getExpense() {
		return expense;
	}
	public void setExpense(float expense) {
		this.expense = expense;
	}
	public float getProfit() {
		return profit;
	}
	public void setProfit(float profit) {
		this.profit = profit;
	}

	public float getTotalHackathonFeesPaid() {
		return totalHackathonFeesPaid;
	}

	public void setTotalHackathonFeesPaid(float totalHackathonFeesPaid) {
		this.totalHackathonFeesPaid = totalHackathonFeesPaid;
	}

	public float getTotalHackathonFeesNotPaid() {
		return totalHackathonFeesNotPaid;
	}

	public void setTotalHackathonFeesNotPaid(float totalHackathonFeesNotPaid) {
		this.totalHackathonFeesNotPaid = totalHackathonFeesNotPaid;
	}

	public float getAvgFeesPaid() {
		return avgFeesPaid;
	}

	public void setAvgFeesPaid(float avgFeesPaid) {
		this.avgFeesPaid = avgFeesPaid;
	} 

}
