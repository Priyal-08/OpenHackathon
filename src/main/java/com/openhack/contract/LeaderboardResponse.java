package com.openhack.contract;

import java.util.List;

public class LeaderboardResponse {

	private long teamId;
	private String teamName;
	private String hackathonName;
	private float teamScore;
	private List <String> teamMembers;
	
	public LeaderboardResponse(String teamName, float teamScore, List<String> teamMembers) {
		super();
		this.teamName = teamName;
		this.teamScore = teamScore;
		this.teamMembers = teamMembers;
	}
	
	public LeaderboardResponse(String teamName, float teamScore) {
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
	public float getTeamScore() {
		return teamScore;
	}
	public void setTeamScore(float teamScore) {
		this.teamScore = teamScore;
	}
	public List<String> getTeamMembers() {
		return teamMembers;
	}
	public void setTeamMembers(List<String> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public LeaderboardResponse(String hackathonName, long teamId, String teamName, float teamScore, List<String> teamMembers) {
		super();
		this.hackathonName = hackathonName;
		this.teamId = teamId;
		this.teamName = teamName;
		this.teamScore = teamScore;
		this.teamMembers = teamMembers;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public String getHackathonName() {
		return hackathonName;
	}

	public void setHackathonName(String hackathonName) {
		this.hackathonName = hackathonName;
	}

}
