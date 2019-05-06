package com.openhack.contract;

public class MemberRequest {

	Judge user;
	OrganizationResponse organization;
	
	public MemberRequest(Judge user, OrganizationResponse organization) {
		super();
		this.user = user;
		this.organization = organization;
	}
	
	public Judge getUser() {
		return user;
	}
	public void setUser(Judge user) {
		this.user = user;
	}
	public OrganizationResponse getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationResponse organization) {
		this.organization = organization;
	}
	
	
}
