package com.openhack.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
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
	
	/** The authcode for first time registration */
	@Column(name = "AUTHCODE")
	private String authcode;

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
	
	public String getAuthCode() {
		return authcode;
	}

	public void setAuthCode(String authcode) {
		this.authcode = authcode;
	}

	public UserAccount() {}
	public UserAccount(UserProfile user, String password, int attempts, String status, String authcode) {
		super();
		this.user = user;
		this.password = password;
		this.attempts = attempts;
		this.status = status;
		this.authcode = authcode;
	}
	


}