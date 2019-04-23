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

/**
 * The Class Team.
 */
@Entity
@Table(name = "TEAM")
public class Team {
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
	@Column(name = "NAME", unique=true)
    private String name;
	
	/** The team members. */
	@OneToMany(mappedBy="team")
	private List<Participant> members;
	
	@Column(name = "PAYMENT_DONE")
	private boolean paymentDone;
	
	@Column(name = "SCORE")
	private long score;
	
	@Column(name = "SUBMISSION_URL")
	private String submissionURL;
	
	@ManyToOne(targetEntity=UserProfile.class, optional=false)
	@JoinColumn(name = "JUDGE_ID",referencedColumnName="ID")
	private UserProfile judge;

}
