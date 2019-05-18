package com.openhack.contract;

public class ParticipantResponse {

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isPaymentDone() {
		return paymentDone;
	}

	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}
	
	public float getFees() {
		return fees;
	}

	public void setFees(float fees) {
		this.fees = fees;
	}

	private long userId;
	
	private String name;
	
	private String title;
	
	private boolean paymentDone;
	
	private float fees;


	public ParticipantResponse(long userId, String name, String title, boolean paymentDone, float fees) {
		super();
		this.userId = userId;
		this.name = name;
		this.title = title;
		this.paymentDone = paymentDone;
		this.fees = fees;
	}

	public ParticipantResponse(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}
}
