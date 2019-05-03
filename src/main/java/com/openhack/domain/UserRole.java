package com.openhack.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "USERROLE")
public class UserRole {
	
	//@EmbeddedId 
	//private UserRoleId id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	
	@ManyToOne(targetEntity=UserProfile.class)
	@JoinColumn(name = "USERID",referencedColumnName="ID")
	private UserProfile user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	/** The role. */
	@Column(name = "ROLE")
	private String role;
	
	public UserRole() {}

	public UserRole(UserProfile user, String role) {
		this.role = role;
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
