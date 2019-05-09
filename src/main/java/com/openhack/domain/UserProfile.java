
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "USERPROFILE")
public class UserProfile{
	public UserProfile(String firstname,String lastname, String email, String title, Address address, String potraitURL,
			String aboutMe, String screenName, Organization organization, String membershipStatus) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.title = title;
		this.address = address;
		this.potraitURL = potraitURL;
		this.aboutMe = aboutMe;
		this.screenName = screenName;
		this.organization = organization;
		this.membershipStatus = membershipStatus;
	}
	
	public String getMembershipStatus() {
		return membershipStatus;
	}

	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}

	public UserProfile(String firstname,String lastname, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.screenName = lastname;
		this.email = email;
		
	}

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	/** The name. */
	@Column(name = "FIRSTNAME")
	private String firstname;

	/** The name. */
	@Column(name = "LASTNAME")
	private String lastname;
	
	/** The email. */
	//TODO: add unique constraint
	@Column(name = "EMAIL")
	private String email;
	
	/** The title. */
	@Column(name = "TITLE")
	private String title;
	
	/** The address. */
	@Embedded
	private Address address;
	
	/** The Potrait URL. */
	@Column(name = "POTRAIT_URL")
	private String potraitURL;

	/** The AboutMe */
	@Column(name = "ABOUT_ME")
	private String aboutMe;

	/** The Screenname */
	
	//TODO: add unique constraint
//	@Min(3)
	@Column(name = "SCREENNAME")
	private String screenName;
	
	/** The organization. */
	@ManyToOne(targetEntity=Organization.class, optional=true)
	@JoinColumn(name = "ORGANIZATION_ID",referencedColumnName="ID")
	private Organization organization;

	@ManyToMany(mappedBy="judges")
	private List<Hackathon> hackathons;
	
	@OneToMany(mappedBy="owner")
	private List<Organization> ownedOrganizations;
	
	/** The Organization membershipStatus */
	@Column(name = "MEMBERSHIP_STATUS")
	private String membershipStatus;
	
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<Organization> getOwnedOrganizations() {
		return ownedOrganizations;
	}

	public void setOwnedOrganizations(List<Organization> ownedOrganizations) {
		this.ownedOrganizations = ownedOrganizations;
	}

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

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
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

}
