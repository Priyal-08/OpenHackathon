package com.openhack.contract;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.openhack.domain.Organization;
import com.openhack.domain.UserProfile;

//Hackathon response class 
@Component
public class HackathonResponse {
	
    private long id;
	
	private String eventName;
	
	private Date startDate;
	
	private Date endDate;
	
	private String description;
	
	private long fees;
	
	private List<UserProfile> judges;
	
	private int minTeamSize;
	
    private int maxTeamSize;
	
	private List<Organization> sponsors;
	
	private float discount;
	
	private int status;
	
	public HackathonResponse() {}
	
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

	public long getFees() {
		return fees;
	}

	public void setFees(long fees) {
		this.fees = fees;
	}

	public List<UserProfile> getJudges() {
		return judges;
	}

	public void setJudges(List<UserProfile> judges) {
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

	public List<Organization> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Organization> sponsors) {
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

	public HackathonResponse(long id, String eventName, Date startDate, Date endDate, String description, long fees,
			List<UserProfile> judges, int minTeamSize, int maxTeamSize, List<Organization> sponsors, float discount,
			int status) {
		super();
		this.id = id;
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

}
