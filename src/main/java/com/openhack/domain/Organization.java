package com.openhack.domain;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class Organization.
 */
@Entity
//@Builder
//@Data
//@AllArgsCOnstructor

@Table(name = "ORGANIZATION")
public class Organization {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
    private Long id;
	
	/** The organization name. */
	//TODO: add unique constraint
	@Column(name = "NAME")
    private String name;
	
	/** The organization owner. */
	@ManyToOne(targetEntity=UserProfile.class, optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID",referencedColumnName="ID")
	private UserProfile owner;
	
	/** The organization description. */
	@Column(name = "DESCRIPTION", nullable=true)
    private String description;
	
	/** The organization address. */
	@Embedded
    private Address address;

	/** The organization members. */
	@OneToMany(mappedBy="organization", fetch = FetchType.LAZY)
	private List<UserProfile> members;
	
	/** The organization owner. */
	@ManyToMany(mappedBy="sponsors", fetch = FetchType.LAZY)
	private List<Hackathon> hackathons;
    
    /**
     * Instantiates a new organization.
     */
    public Organization() {
    	// do nothing
    }
    
    
//    
//    /**
//     * Instantiates a new organization.
//     *
//     * @param name the organization name
//     * @param owner the organization owner
//     * @param description the organization description
//     * @param address the organization address
//     */
//    public Organization(String name, UserProfile owner, String description, Address address ) {
//    	this.name = name;
//    	this.owner = owner;
//    	this.description = description;
//    	this.address = address;
//    	this.members = new ArrayList<UserProfile>();
//    }
//    
//    /**
//    /**
//     * Instantiates a new organization.
//     *
//     * @param id the organization id
//     * @param name the organization name
//     * @param owner the organization owner
//     * @param description the organization description
//     * @param address the organization address
//     */
//    public Organization(Long id, String name, UserProfile owner, String description, Address address ) {
//    	this.id = id;
//    	this.name = name;
//    	this.owner = owner;
//    	this.description = description;
//    	this.address = address; 	
//    }

	public Organization(String name, UserProfile owner, String description, Address address) {
		super();
		this.name = name;
		this.owner = owner;
		this.description = description;
		this.address = address;
	}


	public Long getId() {
		return id;
	}

//	public void setId(Long id) {
//		this.id = id;
//	}

	public List<Hackathon> getHackathons() {
		return hackathons;
	}

	public void setHackathons(List<Hackathon> hackathons) {
		this.hackathons = hackathons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserProfile getOwner() {
		return owner;
	}

	public void setOwner(UserProfile owner) {
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

	public List<UserProfile> getMembers() {
		return members;
	}

	public void setMembers(List<UserProfile> members) {
		this.members = members;
	}
}
