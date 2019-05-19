package com.openhack.contract;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PaymentReportResponse {
	
	private String eventName;	
	private Date startDate;
	private Date endDate;	
	private String description;
	private int noOfTeams; 
	private int noOfSponsors;
	private Map <String,String> paidTeams;
	private Map <String, String> unpaidTeams;
	private Map <String, String> paidParticipants;
	private Map <String, String> unpaidParticipants;




}
