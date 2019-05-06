package com.openhack.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENT")
public class Payment {
	
	public Payment() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
    private long id;
	
	@Column(name = "AMOUNT")
	private long amount;
	
	@ManyToOne(targetEntity=UserProfile.class)
	@JoinColumn(name = "USERID",referencedColumnName="ID")
	private UserProfile user;
	
	@ManyToOne(targetEntity=Hackathon.class)
	@JoinColumn(name = "HACKATHONID",referencedColumnName="ID")
	private Hackathon hackathon;

	
	public Payment(long amount, UserProfile user,Hackathon hackathon ) {
		this.amount = amount;
		this.user = user;
		this.hackathon = hackathon;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
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
	
	
}
