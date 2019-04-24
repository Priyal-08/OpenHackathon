package com.openhack.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERACCOUNT")
public class UserAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	/** The username. */
//	@Id
//	@OneToOne(targetEntity=UserProfile.class)
//	@JoinColumn(name = "USERNAME",referencedColumnName="SCREENNAME")
//	private UserProfile user;
	
	@Id
	@OneToOne(targetEntity=UserProfile.class)
	@JoinColumn(name = "USERID",referencedColumnName="ID")
	private UserProfile user;

	/** The password. */
	@Column(name = "PASSWORD")
	private String password;
	
	/** The attempts */
	@Column(name = "ATTEMPTS")
	private int attempts;
		
	/** The status */
	@Column(name = "STATUS")
	private String status;

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserAccount(UserProfile user, String password, int attempts, String status) {
		super();
		this.user = user;
		this.password = password;
		this.attempts = attempts;
		this.status = status;
	}

}