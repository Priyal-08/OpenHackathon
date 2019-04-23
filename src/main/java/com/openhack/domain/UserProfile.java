
package com.openhack.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERPROFILE")
public class UserProfile {
	public UserProfile(long id, String name, String email, String title, Address address, String potraitURL,
			String aboutMe, String screenName, Organization organization) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.title = title;
		this.address = address;
		this.potraitURL = potraitURL;
		this.aboutMe = aboutMe;
		this.screenName = screenName;
		this.organization = organization;
	}

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	/** The name. */
	@Column(name = "NAME")
	private String name;
	
	/** The email. */
	@Column(name = "EMAIL", unique = true)
	private String email;
	
	/** The title. */
	@Column(name = "TITLE")
	private String title;
	
	/** The address. */
	@Embedded
	private Address address;
	
	/** The Potrait URL. */
	@Column(name = "POTRAITURL")
	private String potraitURL;

	/** The AboutMe */
	@Column(name = "ABOUTME")
	private String aboutMe;

	/** The Screenname */
	@Column(name = "SCREENNAME", unique = true)
	private String screenName;
	
	/** The organization. */
	@ManyToOne(targetEntity=Organization.class, optional=true)
	@JoinColumn(name = "ORGANIZATION_ID",referencedColumnName="ID")
	private Organization organization;
	
	/** The organization. */
	@ManyToOne(targetEntity=Organization.class, optional=true)
	@JoinColumn(name = "ORGANIZATION_ID",referencedColumnName="ID")
	private Organization pendingMembership;
	
	@ManyToMany(mappedBy="sponsors")
	private List<Hackathon> hackathons;
	
	/**
	 * Instantiates a new employee.
	 */
	public UserProfile() {}

	public List<Hackathon> getHackathons() {
		return hackathons;
	}

	public void setHackathons(List<Hackathon> hackathons) {
		this.hackathons = hackathons;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPotraitURL() {
		return potraitURL;
	}

	public void setPotraitURL(String potraitURL) {
		this.potraitURL = potraitURL;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	public Organization getPendingMembership() {
		return pendingMembership;
	}

	public void setPendingMembership(Organization pendingMembership) {
		this.pendingMembership = pendingMembership;
	}
	

}
