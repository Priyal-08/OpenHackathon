package com.openhack.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.MyHackathonResponse;
import com.openhack.contract.MyTeamResponse;
import com.openhack.contract.ParticipantDetail;
import com.openhack.contract.ParticipantResponse;
import com.openhack.contract.UserResponse;
import com.openhack.dao.HackathonDao;
import com.openhack.dao.ParticipantDao;
import com.openhack.dao.UserDao;
import com.openhack.domain.Hackathon;
import com.openhack.domain.Participant;
import com.openhack.domain.Payment;
import com.openhack.domain.Team;
import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
import com.openhack.exception.DuplicateException;
import com.openhack.exception.NotFoundException;

@Service
public class ParticipantService {
	
	@Autowired
	private EmailService emailService;
	
	/** The user dao. */
	@Autowired
	private UserDao userDao;
	
	/** The participant dao. */
	@Autowired
	private ParticipantDao participantDao;
	
	/** The hackathon dao. */
	@Autowired
	private HackathonDao hackathonDao;
	
	@Autowired ErrorResponse errorResponse;
	 
	
	
	@Transactional
	public ResponseEntity<?> getHackathonList(long id) {
		try {
			
			UserProfile user = userDao.findById(id);
			
			// If the user with given id does not exist, return NotFound.
			if(user==null)
				throw new NotFoundException("User", "Id", id);
			
			List<MyHackathonResponse> hackathonResponse = new ArrayList<MyHackathonResponse>();
			// Get all hackathons judged by user.
			hackathonResponse.addAll(user.getHackathons().stream().map(hackathon->new MyHackathonResponse(
					hackathon.getId(), 
					hackathon.getEventName(),
					hackathon.getStartDate(),
					hackathon.getEndDate(),
					hackathon.getDescription(),
					2)).collect(Collectors.toList())); // Role: 2 = Judge
			
			List<BigInteger> hackathonIdsList = participantDao.findHackathonByUserId(id);
			
			// If the user is registered in hackathons as hacker, add them in result list.
			if(hackathonIdsList!=null) {
				List<Hackathon> hackathons = hackathonIdsList.stream().map(hackathonId->hackathonDao.findById(hackathonId.longValue())).collect(Collectors.toList());
				hackathonResponse.addAll(hackathons.stream().map(hackathon->new MyHackathonResponse(
						hackathon.getId(), 
						hackathon.getEventName(),
						hackathon.getStartDate(),
						hackathon.getEndDate(),
						hackathon.getDescription(),
						1)).collect(Collectors.toList())); // Role: 1 = hacker
			}
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(hackathonResponse);
		}
		catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			//return ResponseEntity.notFound().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}

	public ResponseEntity<?> getHackathonDetails(long userId, long hackathonId) {
		try {
			
			UserProfile user = userDao.findById(userId);
			
			// If the user with given id does not exist, return NotFound.
			if(user==null)
				throw new NotFoundException("User", "id", userId);
			
			Hackathon hackathon = hackathonDao.findById(hackathonId);
			// If the hackathon with given id does not exist, return NotFound.
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "id", hackathonId);
			
			Team team = participantDao.findTeamByUserIdAndHackathonId(userId, hackathonId);
			MyTeamResponse myTeamResponse = null;
			if(team!=null)
			{
				List<ParticipantResponse> participants = team.getMembers().stream().map(p->new ParticipantResponse(
						p.getUser().getId(),
						p.getUser().getFirstName(),
						p.getTitle(),
						p.getPaymentDone(),p.getFees(), p.getPaymentDate())).collect(Collectors.toList());
				myTeamResponse = new MyTeamResponse(hackathon.getId(), hackathon.getEventName(), team.getId(), team.getName(), participants, team.getPaymentDone(),
			team.getScore(), team.getSubmissionURL(), team.getTeamLead().getId(), hackathon.getStatus());
			}
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(myTeamResponse);
		}
		catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			//return ResponseEntity.notFound().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	public ResponseEntity<?> getTeamDetails(long userId, long teamId) {
		try {
			
			UserProfile user = userDao.findById(userId);
			
			// If the user with given id does not exist, return NotFound.
			if(user==null)
				throw new NotFoundException("User", "id", userId);
			
			//Hackathon hackathon = hackathonDao.findById(hackathonId);
			// If the hackathon with given id does not exist, return NotFound.
//			if(hackathon==null)
//				throw new NotFoundException("Hackathon", "id", hackathonId);
			
			Team team = participantDao.findById(teamId);
			MyTeamResponse myTeamResponse = null;
			if(team!=null)
			{
				List<ParticipantResponse> participants = team.getMembers().stream().map(p->new ParticipantResponse(
						p.getUser().getId(),
						p.getUser().getFirstName(),
						p.getTitle(),
						p.getPaymentDone(),p.getFees(),p.getPaymentDate())).collect(Collectors.toList());
				myTeamResponse = new MyTeamResponse(team.getHackathon().getId(), team.getHackathon().getEventName(), team.getId(), team.getName(), participants, team.getPaymentDone(),
			team.getScore(), team.getSubmissionURL(), team.getTeamLead().getId(), team.getHackathon().getStatus());
			}
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(myTeamResponse);
		}
		catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			//return ResponseEntity.notFound().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	/**
	 * Register team for hackathon
	 *
	 * @param userId: the user id
	 * @param hackathonId: the hackathonId id
	 * @param members: team members
	 * @return ResponseEntity: the hackathon details object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> registerTeam(long userId, long hackathonId, String teamName, List<ParticipantDetail> members) {
		try {
			Hackathon hackathon = hackathonDao.findById(hackathonId);
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", hackathonId);
			
			UserProfile teamLead = userDao.findById(userId);
			if(teamLead==null)
				throw new NotFoundException("User", "Id", userId);
			
			Team t = participantDao.findTeamByNameAndHackathon(teamName, hackathonId);
			if(t!=null)
				throw new DuplicateException("Team", "name", teamName);
			
			List<UserProfile> memberList = new ArrayList<UserProfile>();
			List<Long> memberIds = members.stream().map(m->m.getId()).collect(Collectors.toList());
			if(members!=null && members.size()>0)
				memberList = userDao.findByIds(memberIds);
			System.out.println("memberList: " + memberList.size());
			
			List<Participant> participants = new ArrayList<Participant>();

			final Team team = participantDao.store(new Team(hackathon, teamLead, teamName, participants, false, 0, null, null));
			
			MyTeamResponse myTeamResponse = null;
			if(team!=null) {
				float discountedAmount = hackathon.getFees()*(100-hackathon.getDiscount());
				participants = memberList.stream().map(member->participantDao.store(new Participant(
						team,
						member,
						UUID.randomUUID().toString(),
						false,
						getRole(members, member.getId()),
						hackathon.getSponsors().contains(member.getOrganization()) && member.getMembershipStatus().equals("Approved") ?discountedAmount:hackathon.getFees()
						))).collect(Collectors.toList());
				
				final List<Participant> finalParticipants = participants;
				
				team.setMembers(participants);
				List<ParticipantResponse> participantsResponse = team.getMembers().stream().map(p->new ParticipantResponse(
						p.getUser().getId(),
						p.getUser().getFirstName(),
						p.getTitle(),
						p.getPaymentDone(), p.getFees(), p.getPaymentDate())).collect(Collectors.toList());
				myTeamResponse = new MyTeamResponse(
						hackathon.getId(),
						hackathon.getEventName(),
						team.getId(), team.getName(),
						participantsResponse,
						team.getPaymentDone(),
						team.getScore(),
						team.getSubmissionURL(),
						team.getTeamLead().getId(), hackathon.getStatus());
				String subject = String.format("%s Registration Payment", hackathon.getEventName());
				
				new Thread(() -> {
					finalParticipants.forEach(
							(p) -> emailService.sendSimpleMessage(p.getUser().getEmail(), subject , generateMailText(p, hackathon)));
				}).start();				
			}
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(myTeamResponse);
		}
		catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(DuplicateException e) {
			errorResponse = new ErrorResponse("Conflict", "409", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
//	/**
//	 * Register team for hackathon
//	 *
//	 * @param id: the participant id
//	 * @param submissionURL: the hackathonId id
//	 * @param members: team members
//	 * @return ResponseEntity: the hackathon details object on success/ error message on error
//	 */
//	@Transactional
//	public ResponseEntity<?> updateSubmissionURL(long id,long hackathonId, String submissionURL) {
//		
//		try {
//			Team team = participantDao.findTeamByUserIdAndHackathonId(id, hackathonId);
//			team.setSubmissionURL(submissionURL);
//			Hackathon hackathon = hackathonDao.findById(hackathonId);
//
//			List<ParticipantResponse> participantsResponse = null;
//			
//			MyTeamResponse myTeamResponse = new MyTeamResponse(
//						hackathon.getId(),
//						hackathon.getEventName(),
//						team.getId(), team.getName(),
//						participantsResponse,
//						team.getPaymentDone(),
//						team.getScore(),
//						team.getSubmissionURL(),
//						team.getTeamLead().getId());
//
//			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(myTeamResponse);
//		}
//	catch(Exception e) {
//			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
//			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
//		}
//	}
//	
	/**
	 * Register team for hackathon
	 *
	 * @param id: the participant id
	 * @param submissionURL: the hackathonId id
	 * @param members: team members
	 * @return ResponseEntity: the hackathon details object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> updateDetails(long id,long hackathonId, String submissionURL, String score) {
		
		try {
			Team team = participantDao.findTeamByUserIdAndHackathonId(id, hackathonId);
			if(score==null) {
				team.setSubmissionURL(submissionURL);
			}
			else {
				UserProfile judge = userDao.findById(id);
				team.setJudge(judge);
				team.setScore(Long.parseLong(score));
			}
			Hackathon hackathon = hackathonDao.findById(hackathonId);

			List<ParticipantResponse> participantsResponse = null;
			
			MyTeamResponse myTeamResponse = new MyTeamResponse(
						hackathon.getId(),
						hackathon.getEventName(),
						team.getId(), team.getName(),
						participantsResponse,
						team.getPaymentDone(),
						team.getScore(),
						team.getSubmissionURL(),
						team.getTeamLead().getId(),
						hackathon.getStatus());

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(myTeamResponse);
		}
	catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	private String generateMailText(Participant p, Hackathon hackathon) {
		String baseURL = "http://localhost:3000/payment-confirmation/?token=";
		return String.format("Hello %s, \n\nPlease make a payment using link below to confirm your registration for hackathon. \n %s \n\n\nTeam %s", p.getUser().getFirstName(), baseURL + p.getPaymentURL(), hackathon.getEventName());
	}
	
	private String getRole(List<ParticipantDetail> members, long userId) {
		for(ParticipantDetail member: members) {
			if(member.getId()==userId)
				return member.getRole();
		}
		return "Other";
	}
	
	@Transactional
	public ResponseEntity<?> pay(String token) {
		ParticipantResponse response = null;
		Payment payment = null;
		try {
			Participant participant =null;
			participant = participantDao.findParticipantByToken(token);
			
			if (participant == null) {	// if token code not found in DB
				errorResponse = new ErrorResponse("BadRequest", "400", "Invalid token");
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}		
			participant.setPaymentDone(true);
			participant.setPaymentDate(LocalDateTime.now().toString());
			
			Team team = participant.getTeam();
			
			List <Participant> participantList = new ArrayList <Participant>();
			participantList =  team.getMembers();// (ArrayList<Participant>) team.getMembers();
			
			int teamSize = participantList.size();
			int teamPayedCount = 1;
			
			for (Participant teamParticipant : participantList) {
				if (participant.getId() != teamParticipant.getId())
					if (teamParticipant.getPaymentDone() == true)
						teamPayedCount += 1;					
			}	
			
			if (teamPayedCount == teamSize) {
				team.setPaymentDone(true);
				team.setPaymentDate(LocalDateTime.now().toString());
				for (Participant teamParticipant : participantList) {
					payment = new Payment(teamParticipant.getFees(), 
							teamParticipant.getUser(),
							team.getHackathon());
					participantDao.store(payment);									
				}
				
				String subject = String.format("Open Hackathon - Your team payment is complete!");
				String text = String.format("Congratulations! Your team has made the payment and you are all set! All the best! \n ");
				String emailId = team.getTeamLead().getEmail();
				new Thread(() -> {
					System.out.println("Sending mail to " + emailId );
					emailService.sendSimpleMessage(emailId, subject , text);
				}).start();

			}
			
			response = new ParticipantResponse(participant.getPaymentDone());					
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	

}
