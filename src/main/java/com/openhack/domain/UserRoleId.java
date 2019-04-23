package com.openhack.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

public class UserRoleId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The username. */
	@OneToOne(targetEntity=UserProfile.class)
	@JoinColumn(name = "USERNAME",referencedColumnName="SCREENNAME")
	private UserProfile user;

	/** The hackathon. */
	@ManyToOne(targetEntity=Hackathon.class)
	@JoinColumn(name = "HACKATHON_ID",referencedColumnName="ID")
	private Hackathon hackathon;
}
