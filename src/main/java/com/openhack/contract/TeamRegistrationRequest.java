package com.openhack.contract;

import java.util.List;

public class TeamRegistrationRequest {
	private String teamName;
	private List<ParticipantDetail> participants;
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<ParticipantDetail> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantDetail> participants) {
		this.participants = participants;
	}

	public TeamRegistrationRequest(String teamName, List<ParticipantDetail> participants) {
		super();
		this.teamName = teamName;
		this.participants = participants;
	}
}
