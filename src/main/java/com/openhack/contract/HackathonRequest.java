package com.openhack.contract;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class HackathonRequest {
	
	public HackathonRequest(String eventName, String startDate, String endDate, String description, long fees,
			List<Long> judges, int minTeamSize, int maxTeamSize, List<Long> sponsors, float discount, int status) {
		super();
		this.eventName = eventName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.fees = fees;
		this.judges = judges;
		this.minTeamSize = minTeamSize;
		this.maxTeamSize = maxTeamSize;
		this.sponsors = sponsors;
		this.discount = discount;
		this.status = status;
	}

	private String eventName;
	private String startDate;
	private String endDate;
	private String description;
	private long fees;
	private List<Long> judges;
	private int minTeamSize;
	private int maxTeamSize;
	private List<Long> sponsors;
	private float discount;
	private int status;

	public HackathonRequest() {}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getFees() {
		return fees;
	}

	public void setFees(long fees) {
		this.fees = fees;
	}

	public List<Long> getJudges() {
		return judges;
	}

	public void setJudges(List<Long> judges) {
		this.judges = judges;
	}

	public int getMinTeamSize() {
		return minTeamSize;
	}

	public void setMinTeamSize(int minTeamSize) {
		this.minTeamSize = minTeamSize;
	}

	public int getMaxTeamSize() {
		return maxTeamSize;
	}

	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}

	public List<Long> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Long> sponsors) {
		this.sponsors = sponsors;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

}
