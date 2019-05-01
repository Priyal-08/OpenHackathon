package com.openhack.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PARTICIPANT")
public class Participant {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
    private long id;
	
	@ManyToOne(targetEntity=Team.class, optional=false)
	@JoinColumn(name = "TEAM_ID",referencedColumnName="ID")
	private Team team;
	
	@OneToOne(targetEntity=UserProfile.class, optional=true)
	@JoinColumn(name = "USER_ID",referencedColumnName="ID")
	private UserProfile user;
	
	@Column(name = "PAYMENT_URL")
	private String paymentURL;
	
	@Column(name = "PAYMENT_DONE")
	private boolean paymentDone;
	
	@Column(name = "TITLE")
	private String title;
	
	@OneToOne(targetEntity=Payment.class, optional=true)
	@JoinColumn(name = "PAYMENT_ID",referencedColumnName="ID")
	private Payment payment;
	
	@Column(name = "FEES", nullable=true)
	private float fees;
	
	
	public Participant() {}
	
	public Participant(UserProfile user, String paymentURL, boolean paymentDone, String title) {
		this.user = user;
		this.paymentURL = paymentURL;
		this.paymentDone = paymentDone;
		this.title = title;
	}
	
	public Participant(Team team, UserProfile user, String paymentURL, boolean paymentDone, String title, float fees) {
		this.team = team;
		this.user = user;
		this.paymentURL = paymentURL;
		this.paymentDone = paymentDone;
		this.title = title;
		this.fees = fees;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}
	
	public String getPaymentURL() {
		return paymentURL;
	}

	public void setPaymentURL(String paymentURL) {
		this.paymentURL = paymentURL;
	}

	public boolean getPaymentDone() {
		return paymentDone;
	}

	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public float getFees() {
		return fees;
	}

	public void setTitle(float fees) {
		this.fees = fees;
	}
}
