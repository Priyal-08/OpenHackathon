package com.openhack.contract;

import java.util.List;

public class HackathonExpenseResponse {
	
	private long id;
	
	private String eventName;
	
	List<ExpenseResponse> expenses;
	
	public HackathonExpenseResponse() {}
	
	public HackathonExpenseResponse(long id, String eventName, List<ExpenseResponse> expenses) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.expenses = expenses;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public List<ExpenseResponse> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<ExpenseResponse> expenses) {
		this.expenses = expenses;
	}
}
