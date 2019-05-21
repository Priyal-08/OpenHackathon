package com.openhack.contract;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

//Hackathon response class 
@Component
public class HackathonResponse {
	
    private long id;
	
	private String eventName;
	
	private Date startDate;
	
	private Date endDate;
	
	private String description;
	
	private float fees;
	
	private List<Judge> judges;
	
	private int minTeamSize;
	
    private int maxTeamSize;
	
	private List<OrganizationResponse> sponsors;
	
	private float discount;
	
	private int status;
	
	private int role;
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

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

	public float getFees() {
		return fees;
	}

	public void setFees(float fees) {
		this.fees = fees;
	}

	public List<Judge> getJudges() {
		return judges;
	}

	public void setJudges(List<Judge> judges) {
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

	public List<OrganizationResponse> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<OrganizationResponse> sponsors) {
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

	public HackathonResponse(long id, String eventName, Date startDate, Date endDate, String description, float fees,
			List<Judge> judges, int minTeamSize, int maxTeamSize, List<OrganizationResponse> sponsors, float discount,
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
