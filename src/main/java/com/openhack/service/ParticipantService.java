package com.openhack.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.MyHackathonResponse;
import com.openhack.contract.MyTeamResponse;
import com.openhack.contract.ParticipantDetail;
import com.openhack.contract.ParticipantResponse;
import com.openhack.dao.HackathonDao;
import com.openhack.dao.ParticipantDao;
import com.openhack.dao.UserDao;
import com.openhack.domain.Hackathon;
import com.openhack.domain.Participant;
import com.openhack.domain.Payment;
import com.openhack.domain.Team;
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

	@Autowired
	private Environment env;

	/** The participant dao. */
	@Autowired
	private ParticipantDao participantDao;

	/** The hackathon dao. */
	@Autowired
	private HackathonDao hackathonDao;

	@Autowired ErrorResponse errorResponse;

	@Transactional(readOnly=true)
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

	@Transactional(readOnly=true)
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

	@Transactional(readOnly=true)
	public ResponseEntity<?> getTeamDetails(long userId, long teamId) {
		try {

			UserProfile user = userDao.findById(userId);

			// If the user with given id does not exist, return NotFound.
			if(user==null)
				throw new NotFoundException("User", "id", userId);

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
	@Transactional(rollbackFor=Exception.class)
	public ResponseEntity<?> registerTeam(long userId, long hackathonId, String teamName, List<ParticipantDetail> members) throws Exception {
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
				float discountedAmount = hackathon.getFees()*(float)(100-hackathon.getDiscount())/100;
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
			throw e;
		}
	}

	/**
	 * Update team details
	 *
	 * @param id: the user id
	 * @param hackathonId: the hackathon Id
	 * @param submissionURL: the code URL
	 * @param score: team's score
	 * @param teamId: team Id
	 * @return ResponseEntity: the hackathon details object on success/ error message on error
	 */
	@Transactional(rollbackFor=Exception.class)
	public ResponseEntity<?> updateDetails(long id,long hackathonId, String submissionURL, float score, long teamId) throws Exception {	
		try {
			Team team = null;
			if(submissionURL!=null && submissionURL!="") {
				team = participantDao.findTeamByUserIdAndHackathonId(id, hackathonId);
				if(team==null)
					throw new NotFoundException("Team could not be found");
				team.setSubmissionURL(submissionURL);
			}
			else {
				team = participantDao.findById(teamId);
				if(team==null)
					throw new NotFoundException("Team", "Id", teamId);
				UserProfile judge = userDao.findById(id);
				team.setJudge(judge);
				team.setScore(score);
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
		catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(Exception e) {
			throw e;
		}
	}

	private String generateMailText(Participant p, Hackathon hackathon) {
		String baseURL = env.getProperty("frontendserver.baseurl");

		baseURL = baseURL + "/payment-confirmation/?token=";
		return String.format("Hello %s, \n\nPlease make a payment using link below to confirm your registration for hackathon. \n %s \n\n\nTeam %s", p.getUser().getFirstName(), baseURL + p.getPaymentURL(), hackathon.getEventName());
	}

	private String getRole(List<ParticipantDetail> members, long userId) {
		for(ParticipantDetail member: members) {
			if(member.getId()==userId)
				return member.getRole();
		}
		return "Other";
	}

	@Transactional(rollbackFor=Exception.class)
	public ResponseEntity<?> pay(String token) throws Exception {
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

			String payeeSubject = String.format("OpenHackathon - Thank you for your payment!");
			String payeeText = String.format("Hi %s, \n\n Thank you for making the payment! Your payment details below. \n\n Payment ID: %s\n Payment Date: %s \n Payment Amount: $%s \n\n\n Thank You, \n Team OpenHackathon", participant.getUser().getFirstname(), participant.getPaymentURL(), participant.getPaymentDate(), participant.getFees());
			String payeeEmailId = participant.getUser().getEmail();

			new Thread(() -> {
				System.out.println("Sending mail to " + payeeEmailId );
				emailService.sendSimpleMessage(payeeEmailId, payeeSubject , payeeText);
			}).start();

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
				String subject = String.format("OpenHackathon - Congratulations, your team is all paid up!");
				String text = String.format("Hi %s, \n\n Congratulations! Your team has made the payment and you are all set! All the best! \n\n Thank You, \n Team OpenHackathon", team.getTeamLead().getFirstname() );
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
			throw e;
		}
	}
}
