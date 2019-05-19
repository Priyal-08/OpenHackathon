package com.openhack.contract;

import java.util.List;

import org.springframework.stereotype.Component;

import com.openhack.domain.Address;

//Organization response class 
@Component
public class OrganizationResponse {

    private Long id;
	
	/** The organization name. */
    private String name;
	
	/** The organization owner. */
	private Judge owner;
	
	/** The organization description. */
    private String description;
	
	/** The organization address. */
    private Address address;
	
	/** The organization members. */
	private List<Judge> members;
	
	private List<MyHackathonResponse> sponsoredHackathons;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Judge getOwner() {
		return owner;
	}

	public void setOwner(Judge owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Judge> getMembers() {
		return members;
	}

	public void setMembers(List<Judge> members) {
		this.members = members;
	}

    public OrganizationResponse() {
    	
    }

	public List<MyHackathonResponse> getSponsoredHackathons() {
		return sponsoredHackathons;
	}

	public void setSponsoredHackathons(List<MyHackathonResponse> sponsoredHackathons) {
		this.sponsoredHackathons = sponsoredHackathons;
	}
	
	public OrganizationResponse(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public OrganizationResponse(Long id, String name, Judge owner, String description, Address address, List<Judge> members, List<MyHackathonResponse> sponsoredHackathons) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.description = description;
		this.address = address;
		this.members = members;
		this.sponsoredHackathons = sponsoredHackathons;
	}
}
