package com.openhack.contract;

import java.util.List;

import com.openhack.domain.Participant;

public class MyTeamResponse {
	
	private long hackathonId;
	
	private String hackathonName;
	
	private long teamId;
	
	private String teamName;
	
	private int status;
	
	private String paymentDate;
	
	private List<ParticipantResponse> participants;
	
	public MyTeamResponse(long hackathonId, String hackathonName, long teamId, String teamName, List<ParticipantResponse> participants, boolean paymentDone,
			float score, String submissionURL, long teamLeadId, int status) {
		super();
		this.hackathonId = hackathonId;
		this.hackathonName = hackathonName;
		this.teamId = teamId;
		this.teamName = teamName;
		this.participants = participants;
		this.paymentDone = paymentDone;
		this.score = score;
		this.submissionURL = submissionURL;
		this.teamLeadId = teamLeadId;
		this.status = status;
	}

	public MyTeamResponse(long hackathonId, String hackathonName, long teamId, String teamName, List<ParticipantResponse> participants, boolean paymentDone, String paymentDate) {
		super();
		this.hackathonId = hackathonId;
		this.hackathonName = hackathonName;
		this.teamId = teamId;
		this.teamName = teamName;
		this.participants = participants;
		this.paymentDone = paymentDone;
		this.paymentDate = paymentDate;
	}

	public long getHackathonId() {
		return hackathonId;
	}

	public void setHackathonId(long hackathonId) {
		this.hackathonId = hackathonId;
	}
	
	public String getHackathonName() {
		return hackathonName;
	}

	public void setHackathonName(String hackathonName) {
		this.hackathonName = hackathonName;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<ParticipantResponse> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantResponse> participants) {
		this.participants = participants;
	}

	public boolean isPaymentDone() {
		return paymentDone;
	}

	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getSubmissionURL() {
		return submissionURL;
	}

	public void setSubmissionURL(String submissionURL) {
		this.submissionURL = submissionURL;
	}

	public long getTeamLeadId() {
		return teamLeadId;
	}

	public void setTeamLeadId(long teamLeadId) {
		this.teamLeadId = teamLeadId;
	}

	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	private boolean paymentDone;
	
	private float score;
	
	private String submissionURL;
	
	private long teamLeadId;

	public MyTeamResponse(String paymentDate) {
		super();
		this.paymentDate = paymentDate;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
}
