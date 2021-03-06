package com.openhack.contract;

import org.springframework.stereotype.Component;

@Component
public class AuthResponse {
	
	private long id; 
	private String firstname;
	private String lastname;
	private String email;
	private String title;
	private String city;
	private String state;
	private String zip;
	private String street;
	private String potraitURL;
	private String aboutMe;
	private String screenName;
	private String organizationName;
	private String status;
	private String role;
	private String membershipStatus;
	
    private String accessToken;
    private String tokenType = "Bearer";
    
public AuthResponse() {}
	
	public AuthResponse(long id, String firstname, String lastname, String email, String title,
			String city, String state, String street, String zip, 
			String potraitURL, String aboutMe, String screenName, String orgName, String membershipStatus, String accessToken) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.title = title;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.potraitURL = potraitURL;
		this.aboutMe = aboutMe;
		this.screenName = screenName;
		this.organizationName = orgName;
		this.membershipStatus = membershipStatus;
		this.accessToken = accessToken;
	}
	
	public AuthResponse(long id, String role, String firstname, String lastname, String accessToken) {
		super();
		this.id = id;
		this.role = role;
		this.firstname = firstname;
		this.lastname = lastname;
		this.accessToken = accessToken;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getTitle() {
		return title;
	}

	public String getPotraitURL() {
		return potraitURL;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPotraitURL(String potraitURL) {
		this.potraitURL = potraitURL;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getMembershipStatus() {
		return membershipStatus;
	}

	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}
	
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
