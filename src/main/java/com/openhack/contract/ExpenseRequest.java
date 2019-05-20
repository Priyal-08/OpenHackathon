package com.openhack.contract;

import org.springframework.stereotype.Component;

@Component
public class ExpenseRequest {
	
	public ExpenseRequest(String title, String description, String expenseDate, float amount) {
		super();
		this.title = title;
		this.description = description;
		this.expenseDate = expenseDate;
		this.amount = amount;
	}

	private String title;
	private String description;
	private String expenseDate;
	private float amount;

	public ExpenseRequest() {}

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

	public String getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

}
