package com.openhack.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.openhack.domain.Hackathon;
import com.openhack.domain.UserProfile;


@Entity
@IdClass(UserRoleId.class)
@Table(name = "USERROLE")
public class UserRole {
	/** The username. */
	@Id
	private UserProfile user;

	/** The hackathon. */
	@Id
	private Hackathon hackathon;
	
	/** The role. */
	@Column(name = "ROLE")
	private String role;

	public UserRole(UserProfile user, Hackathon hackathon, String role) {
		this.user = user;
		this.hackathon = hackathon;
		this.role = role;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public Hackathon getHackathon() {
		return hackathon;
	}

	public void setHackathon(Hackathon hackathon) {
		this.hackathon = hackathon;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
