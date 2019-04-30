package com.openhack.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * The Class Team.
 */
@Entity
@Table(name = "TEAM")
public class Team {
	
	public Team() {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Hackathon getHackathon() {
		return hackathon;
	}

	public void setHackathon(Hackathon hackathon) {
		this.hackathon = hackathon;
	}

	public UserProfile getTeamLead() {
		return teamLead;
	}

	public void setTeamLead(UserProfile teamLead) {
		this.teamLead = teamLead;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Participant> getMembers() {
		return members;
	}

	public void setMembers(List<Participant> members) {
		this.members = members;
	}

	public boolean getPaymentDone() {
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

	public UserProfile getJudge() {
		return judge;
	}

	public void setJudge(UserProfile judge) {
		this.judge = judge;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
    private long id;
	
	@ManyToOne(targetEntity=Hackathon.class, optional=false)
	@JoinColumn(name = "HACKATHON_ID",referencedColumnName="ID")
	private Hackathon hackathon;
	
	@ManyToOne(targetEntity=UserProfile.class, optional=false)
	@JoinColumn(name = "TEAMLEAD_ID",referencedColumnName="ID")
	private UserProfile teamLead;
	
	/** The team name. */
	//TODO: add unique constraint
	@Column(name = "NAME")
    private String name;
	
	/** The team members. */
	@OneToMany(mappedBy="team")
	private List<Participant> members;
	
	@Column(name = "PAYMENT_DONE")
	private boolean paymentDone;
	
	@Min(0)
	@Max(10)
	@Column(name = "SCORE")
	private long score;
	
	@Column(name = "SUBMISSION_URL")
	private String submissionURL;
	
	@ManyToOne(targetEntity=UserProfile.class, optional=false)
	@JoinColumn(name = "JUDGE_ID",referencedColumnName="ID")
	private UserProfile judge;

}
