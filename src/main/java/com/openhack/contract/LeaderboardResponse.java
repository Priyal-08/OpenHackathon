package com.openhack.contract;

import java.util.List;

import com.openhack.domain.Participant;

public class LeaderboardResponse {

	private String teamName;
	private long teamScore;
	private List <String> teamMembers;
	
	public LeaderboardResponse(String teamName, long teamScore, List<String> teamMembers) {
		super();
		this.teamName = teamName;
		this.teamScore = teamScore;
		this.teamMembers = teamMembers;
	}
	
	public LeaderboardResponse(String teamName, long teamScore) {
		super();
		this.teamName = teamName;
		this.teamScore = teamScore;
	}

	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public long getTeamScore() {
		return teamScore;
	}
	public void setTeamScore(long teamScore) {
		this.teamScore = teamScore;
	}
	public List<String> getTeamMembers() {
		return teamMembers;
	}
	public void setTeamMembers(List<String> teamMembers) {
		this.teamMembers = teamMembers;
	}

}
