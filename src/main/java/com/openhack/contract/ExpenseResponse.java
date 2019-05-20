package com.openhack.contract;

import java.util.Date;

public class ExpenseResponse {

	private long id;
	private String title;
	private String description;
	private Date expenseDate;
	private float amount;

	public ExpenseResponse() {}
	
	public ExpenseResponse(long id, String title, String description, Date expenseDate, float amount) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.expenseDate = expenseDate;
		this.amount = amount;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

}
