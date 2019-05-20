package com.openhack.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	public Team(Hackathon hackathon, UserProfile teamLead, String name, List<Participant> members, boolean paymentDone,
			@Min(0) @Max(10) long score, String submissionURL, UserProfile judge) {
		super();
		this.hackathon = hackathon;
		this.teamLead = teamLead;
		this.name = name;
		this.members = members;
		this.paymentDone = paymentDone;
		this.score = score;
		this.submissionURL = submissionURL;
		this.judge = judge;
	}

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
	
	@ManyToOne(targetEntity=Hackathon.class, optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "HACKATHON_ID",referencedColumnName="ID")
	private Hackathon hackathon;
	
	@ManyToOne(targetEntity=UserProfile.class, optional=false, fetch=FetchType.LAZY)
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
	
	@Column(name = "PAYMENT_DATE")
	private String paymentDate;
	
	@Min(0)
	@Max(10)
	@Column(name = "SCORE")
	private float score;
	
	@Column(name = "SUBMISSION_URL")
	private String submissionURL;
	
	@ManyToOne(targetEntity=UserProfile.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "JUDGE_ID",referencedColumnName="ID")
	private UserProfile judge;

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

}
