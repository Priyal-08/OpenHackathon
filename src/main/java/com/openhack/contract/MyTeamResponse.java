package com.openhack.contract;

import java.util.List;

public class MyTeamResponse {
	
	private long hackathonId;
	
	private String hackathonName;
	
	private long teamId;
	
	private String teamName;
	
	private int status;
	
	private List<ParticipantResponse> participants;
	
	public MyTeamResponse(long hackathonId, String hackathonName, long teamId, String teamName, List<ParticipantResponse> participants, boolean paymentDone,
			long score, String submissionURL, long teamLeadId, int status) {
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

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
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
	
	private long score;
	
	private String submissionURL;
	
	private long teamLeadId;
	
}
