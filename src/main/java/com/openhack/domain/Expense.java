package com.openhack.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EXPENSE")
public class Expense {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
    private long id;
	
	@Column(name = "AMOUNT")
	private float amount;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "EXPENSE_DATE")
    private Date date;
	
	@ManyToOne(targetEntity=Hackathon.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "HACKATHONID",referencedColumnName="ID")
	private Hackathon hackathon;
	
	public Expense() {}
	
	public Expense(long id, float amount, String title, String description, Date date, Hackathon hackathon) {
		super();
		this.id = id;
		this.amount = amount;
		this.title = title;
		this.description = description;
		this.date = date;
		this.hackathon = hackathon;
	}

	public Expense(float amount, String title, String description, Date date, Hackathon hackathon) {
		super();
		this.amount = amount;
		this.title = title;
		this.description = description;
		this.date = date;
		this.hackathon = hackathon;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Hackathon getHackathon() {
		return hackathon;
	}

	public void setHackathon(Hackathon hackathon) {
		this.hackathon = hackathon;
	}

}
