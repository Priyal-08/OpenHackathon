package com.openhack.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.openhack.contract.EmptyResponse;
import com.openhack.contract.ErrorResponse;
import com.openhack.contract.FinanceReportResponse;
import com.openhack.contract.HackathonResponse;
import com.openhack.contract.OrganizationResponse;
import com.openhack.contract.ParticipantResponse;
import com.openhack.contract.Judge;
import com.openhack.contract.LeaderboardResponse;
import com.openhack.contract.MyTeamResponse;
import com.openhack.dao.HackathonDao;
import com.openhack.dao.OrganizationDao;
import com.openhack.dao.ParticipantDao;
import com.openhack.dao.UserDao;
import com.openhack.domain.Hackathon;
import com.openhack.domain.Organization;
import com.openhack.domain.Participant;
import com.openhack.domain.Team;
import com.openhack.domain.UserProfile;
import com.openhack.exception.DuplicateException;
import com.openhack.exception.InvalidArgumentException;
import com.openhack.exception.NotFoundException;

@Service
public class HackathonService {

	/** The hackathon dao. */
	@Autowired
	private HackathonDao hackathonDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private UserDao userDao;
	
	/** The participant dao. */
	@Autowired
	private ParticipantDao participantDao;
	
	/** The response. */
	@Autowired	
	private HackathonResponse response;
	
//	/** The response. */
//	@Autowired	
//	private FinanceReportResponse finReportResponse;

	
	@Autowired ErrorResponse errorResponse;
	
	/**
	 * Creates the hackathon.
	 *
	 * @param eventName: event name
	 * @param description: event description
	 * @param startDate: event start date
	 * @param endDate: event end date
	 * @param fees: event registration fees
	 * @param minTeamSize: minimum team size to participate in event
	 * @param maxTeamSize: maximum team size to participate in event
	 * @param judges:  event judges
	 * @param sponsors: event sponsors
	 * @param discount: event discount
	 * @return ResponseEntity: newly created event object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> createHackathon(String eventName, String description, String sDate, String eDate, long fees,
			int minTeamSize, int maxTeamSize, List<Long> judges, List<Long> sponsors, float discount) {
		Hackathon hackathon=null;
		Date today = new Date();
		try {
			SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd"); 
			Date startDate = formatter.parse(sDate);
			Date endDate = formatter.parse(eDate);
			
			// If the event name is null, return BadRequest.
			if(eventName == null || today.compareTo(startDate) >0 || startDate.compareTo(endDate)>0) {
				throw new InvalidArgumentException("eventName/startDate/endDate");
			}
			
			
			// Event name should be unique so if an event with given name already exist, return BadRequest.
			hackathon = hackathonDao.findByEventName(eventName);
			
			if(hackathon!=null) {
				throw new DuplicateException("Hackathon", "eventName", eventName);
			}
			List<Organization> sponsorsList = new ArrayList<Organization>();
			if(sponsors!=null && sponsors.size()>0)
				sponsorsList = organizationDao.findByIds(sponsors);
			List<UserProfile> judgesList = new ArrayList<UserProfile>();
			if(judges!=null && judges.size()>0)
				judgesList = userDao.findByIds(judges);
			hackathon = new Hackathon(eventName, startDate, endDate, description, fees,
					judgesList, minTeamSize, maxTeamSize, sponsorsList, discount);
			hackathon = hackathonDao.store(hackathon);
			
			response = new HackathonResponse(
					hackathon.getId(), 
					hackathon.getEventName(),
					hackathon.getStartDate(),
					hackathon.getEndDate(),
					hackathon.getDescription(),
					hackathon.getFees(),
					hackathon.getJudges().stream().map(judge->new Judge(
							judge.getId(), 
							judge.getFirstName())).collect(Collectors.toList()),
					hackathon.getMinTeamSize(),
					hackathon.getMaxTeamSize(),
					hackathon.getSponsors().stream().map(sponsor->new OrganizationResponse(sponsor.getId(), sponsor.getName())).collect(Collectors.toList()),
					hackathon.getDiscount(),
					hackathon.getStatus());
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(InvalidArgumentException e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
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
	
	/**
	 * Gets all hackathons.
	 *
	 * @return ResponseEntity: list of hackathon objects on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> getHackathons() {
		try {
			List<Hackathon> hackathons = hackathonDao.findAll();
			
			List<HackathonResponse> hackathonResponse = hackathons.stream().map(hackathon->new HackathonResponse(
					hackathon.getId(), 
					hackathon.getEventName(),
					hackathon.getStartDate(),
					hackathon.getEndDate(),
					hackathon.getDescription(),
					hackathon.getFees(),
					hackathon.getJudges().stream().map(judge->new Judge(
							judge.getId(), 
							judge.getFirstName())).collect(Collectors.toList()),
					hackathon.getMinTeamSize(),
					hackathon.getMaxTeamSize(),
					hackathon.getSponsors().stream().map(sponsor->new OrganizationResponse(sponsor.getId(), sponsor.getName())).collect(Collectors.toList()),
					hackathon.getDiscount(),
					hackathon.getStatus())).collect(Collectors.toList()); 
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(hackathonResponse);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	/**
	 * Gets the hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: the hackathon object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> getHackathon(long id, long userId) {
		try {
			Hackathon hackathon = hackathonDao.findById(id);
			
			// If the hackathon with given id does not exist, return NotFound.
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", id);
			int role=0;
			
			// If user is a judge in hackathon
			if(hackathon.getJudges().stream().map(judge->judge.getId()).collect(Collectors.toList()).contains(userId))
				role=2;
			// If user is a participating in hackathon
			else if(participantDao.findTeamByUserIdAndHackathonId(userId, id) !=null)
				role=1;
			response = new HackathonResponse(
					hackathon.getId(), 
					hackathon.getEventName(),
					hackathon.getStartDate(),
					hackathon.getEndDate(),
					hackathon.getDescription(),
					hackathon.getFees(),
					hackathon.getJudges().stream().map(judge->new Judge(
							judge.getId(), 
							judge.getFirstName())).collect(Collectors.toList()),
					hackathon.getMinTeamSize(),
					hackathon.getMaxTeamSize(),
					hackathon.getSponsors().stream().map(sponsor->new OrganizationResponse(sponsor.getId(), sponsor.getName())).collect(Collectors.toList()),
					hackathon.getDiscount(),
					hackathon.getStatus());
			response.setRole(role);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
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
	 * Updates the hackathon.
	 *
	 * @param id: hackathon id
	 * @param eventName: event name
	 * @param description: event description
	 * @param startDate: event start date
	 * @param endDate: event end date
	 * @param fees: event registration fees
	 * @param minTeamSize: minimum team size to participate in event
	 * @param maxTeamSize: maximum team size to participate in event
	 * @param judges:  event judges
	 * @param sponsors: event sponsors
	 * @param discount: event discount
	 * @return ResponseEntity: hackathon object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> updateHackathon(long id, String eventName, String description, String sDate, String eDate, long fees,
			int minTeamSize, int maxTeamSize, List<Long> judges, List<Long> sponsors, float discount) {
		Hackathon hackathon=null;
		Date today = new Date();
		try {
			SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd"); 
			Date startDate = formatter.parse(sDate);
			Date endDate = formatter.parse(eDate);
			// If the event name is null, return BadRequest.
			if(eventName == null || today.compareTo(startDate) >0 || startDate.compareTo(endDate)>0) {
				throw new InvalidArgumentException("eventName/startDate/endDate");
			} 
			
			// If the hackathon with given id does not exist, return NotFound.
			hackathon = hackathonDao.findById(id);
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", id);
			
			// If the hackathon is finalised, no further changes are allowed
			if(hackathon.getStatus()==3)
				throw new InvalidArgumentException("status");
			
			// Event name should be unique so if an event with given name already exist, return BadRequest.
			hackathon = hackathonDao.findByEventName(eventName);
			if((hackathon!=null && hackathon.getId() != id))
				throw new DuplicateException("Hackathon", "eventName", eventName);
			
			List<Organization> sponsorsList = new ArrayList<Organization>();
			if(sponsors!=null && sponsors.size()>0)
				sponsorsList = organizationDao.findByIds(sponsors);
			List<UserProfile> judgesList = new ArrayList<UserProfile>();
			if(judges!=null && judges.size()>0)
				judgesList = userDao.findByIds(judges);
			hackathon = new Hackathon(id, eventName, startDate, endDate, description, fees,
					judgesList, minTeamSize, maxTeamSize, sponsorsList, discount);
			hackathonDao.store(hackathon);
			response = new HackathonResponse(
					hackathon.getId(), 
					hackathon.getEventName(),
					hackathon.getStartDate(),
					hackathon.getEndDate(),
					hackathon.getDescription(),
					hackathon.getFees(),
					hackathon.getJudges().stream().map(judge->new Judge(
							judge.getId(), 
							judge.getFirstName())).collect(Collectors.toList()),
					hackathon.getMinTeamSize(),
					hackathon.getMaxTeamSize(),
					hackathon.getSponsors().stream().map(sponsor->new OrganizationResponse(sponsor.getId(), sponsor.getName())).collect(Collectors.toList()),
					hackathon.getDiscount(),
					hackathon.getStatus());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(InvalidArgumentException e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			//return ResponseEntity.notFound().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
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
	
	
	/**
	 * Updates the hackathon status.
	 *
	 * @param id: hackathon id
	 * @param status: hackathon status
	 * @return ResponseEntity: hackathon object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> updateHackathonStatus(long id, int status) {
		Hackathon hackathon=null;
		try {
			// If the hackathon with given id does not exist, return NotFound.
			hackathon = hackathonDao.findById(id);
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", id);
			
			// If the hackathon is finalised, no further changes are allowed
			if(hackathon.getStatus()==3)
				throw new InvalidArgumentException("status");
			
			// The hackathon should not be finalised if the grading is not done for all teams
			if(status==3) {
				List<Team> teams = participantDao.findTeamsByHackathonId(id);
				for(Team team: teams) {
					if(team.getSubmissionURL()!=null && team.getSubmissionURL()!="" && team.getJudge()==null)
						throw new InvalidArgumentException("status");
				}
			}
			
			// Update hackathon status
			hackathon.setStatus(status);
			response = new HackathonResponse();  // TODO: create hackathon response
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(InvalidArgumentException e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
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
	 * Deletes a hackathon.
	 *
	 * @param id: hackathon id
	 * @return ResponseEntity: deleted hackathon object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> deleteHackathon(long id) {
		Hackathon hackathon=null;
		try {
			// If the hackathon with given id does not exist, return NotFound.
			hackathon = hackathonDao.findById(id);
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", id);
			
			// If the hackathon is finalised or closed, deletion is not allowed
			if(hackathon.getStatus() == 2 || hackathon.getStatus()==3)
				throw new InvalidArgumentException("status");
			
			// Delete the hackathon
			hackathonDao.delete(hackathon);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(new EmptyResponse());
		}
		catch(InvalidArgumentException e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
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
	 * Gets the hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: the hackathon object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> getFinancialReport(long id) {
		try {

			FinanceReportResponse finReportResponse;		
			Hackathon hackathon = hackathonDao.findById(id);
			// If the hackathon with given id does not exist, return NotFound.
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", id);

			int noOfTeams = 0;
			int noOfSponsors = hackathon.getSponsors().size();
			float sponsorsAmount = noOfSponsors*1000;

			int noOfParticipants = 0;
			float feesPaid = 0;
			float avgFeesPaid = 0;
			float feesNotPaid = 0;

			float hackathonFees = hackathon.getFees();
			float revenue = sponsorsAmount;
			float expenses = 0;
			float profit = 0;

			List <Team> teams = participantDao.findTeamsByHackathonId(id); 

			if (teams != null) {
				for (int i = 0; i < teams.size(); i++) {
					Team t = teams.get(i);
					if (t.getPaymentDone())
						noOfTeams+=1;

					List <Participant> participants = participantDao.findParticipantsByTeam(t.getId());
					for (int j = 0 ; j< participants.size(); j++) {
						Participant p = participants.get(j);
						if (p.getPaymentDone()) {
							feesPaid += p.getFees();
							noOfParticipants += 1;
						} else {
							feesNotPaid += p.getFees();
						}
					}
				}
			}

			revenue += feesPaid;
			avgFeesPaid = feesPaid/noOfParticipants;
			profit = revenue - expenses;		

			finReportResponse = new FinanceReportResponse(hackathon.getEventName(), hackathon.getStartDate(), hackathon.getEndDate(), hackathon.getDescription(),
					noOfTeams,noOfSponsors,noOfParticipants,hackathonFees,feesPaid,feesNotPaid,avgFeesPaid, revenue,
					expenses, profit);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(finReportResponse);
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
	 * Gets the hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: the hackathon object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> getPaymentReport(long id) {
		try {
			Hackathon hackathon = hackathonDao.findById(id);
			// If the hackathon with given id does not exist, return NotFound.
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", id);

			List <Team> teams = participantDao.findTeamsByHackathonId(id);	
			List <MyTeamResponse> response = new ArrayList <MyTeamResponse>();
			
			if (teams != null) {
				for (int i = 0; i < teams.size(); i++) {
					Team t = teams.get(i);
					List<ParticipantResponse> participantsResponse = t.getMembers().stream().map(p->new ParticipantResponse(
							p.getUser().getId(),
							p.getUser().getFirstName(),
							p.getTitle(),
							p.getPaymentDone(), p.getFees(), p.getPaymentDate())).collect(Collectors.toList());
					MyTeamResponse teamResponse = new MyTeamResponse(hackathon.getId(), hackathon.getEventName(), t.getId(),t.getName(),participantsResponse, t.getPaymentDone(), t.getPaymentDate());
					response.add(teamResponse);
				}
			}			
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
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
	 * Gets the hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: the hackathon object on success/ error message on error
	 */
	@Transactional	
	public ResponseEntity<?> getLeaderboard(long id) {
		try {

			List<LeaderboardResponse> leaderboardResponse = new ArrayList <LeaderboardResponse>();
			Hackathon hackathon = hackathonDao.findById(id);
			// If the hackathon with given id does not exist, return NotFound.
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", id);
			
			if (hackathon.getStatus() < 2)
				throw new Exception("Hackathon");			

			List <Team> teams = participantDao.findTeamsByHackathonId(id); 
			System.out.println("jsonteam = " + teams);
			if (teams != null) {
				for (int i = 0; i < teams.size(); i++) {
					Team t = teams.get(i);
					if (t.getPaymentDone()) {
						List <String> names = new ArrayList <String>();
						List <Participant> teamMembers = t.getMembers();
						for (int j = 0; j < teamMembers.size(); j++) {
							UserProfile u = teamMembers.get(j).getUser();
							names.add(u.getFirstname() + "," + u.getLastname());
						}											
						LeaderboardResponse lbResp = new LeaderboardResponse(hackathon.getEventName(), t.getId(),t.getName(), t.getScore(), names);
						leaderboardResponse.add(lbResp);																
						}
					}
				}	

		    Collections.sort(leaderboardResponse, new Comparator<LeaderboardResponse>() {
		        @Override public int compare(LeaderboardResponse p1, LeaderboardResponse p2) {
		        	return (Float.valueOf(p2.getTeamScore())).compareTo(Float.valueOf(p1.getTeamScore()));
//		            return (int)p2.getTeamScore() - (int)p1.getTeamScore(); // Descending
		        }
		    });
		    
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(leaderboardResponse);
		
		}
		catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
}
