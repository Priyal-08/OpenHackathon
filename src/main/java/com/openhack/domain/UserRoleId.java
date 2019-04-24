package com.openhack.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserRoleId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserRoleId(UserProfile user, Hackathon hackathon) {
		super();
		this.user = user;
		this.hackathon = hackathon;
	}

	/** The username. */
	@ManyToOne(targetEntity=UserProfile.class)
	@JoinColumn(name = "ID",referencedColumnName="ID", insertable = false, updatable = false)
	private UserProfile user;

	/** The hackathon. */
	@ManyToOne(targetEntity=Hackathon.class)
	@JoinColumn(name = "HACKATHON_ID",referencedColumnName="ID", nullable=true, insertable = false, updatable = false)
	private Hackathon hackathon;

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
}
