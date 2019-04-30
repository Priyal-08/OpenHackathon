package com.openhack.service;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigInteger;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.MyHackathonResponse;
import com.openhack.contract.MyTeamResponse;
import com.openhack.contract.ParticipantResponse;
import com.openhack.dao.HackathonDao;
import com.openhack.dao.ParticipantDao;
import com.openhack.dao.UserDao;
import com.openhack.domain.Hackathon;
import com.openhack.domain.Team;
import com.openhack.domain.UserProfile;
import com.openhack.exception.NotFoundException;

@Service
public class ParticipantService {
	
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
						p.getUser().getName(),
						p.getTitle(),
						p.getPaymentDone())).collect(Collectors.toList());
				myTeamResponse = new MyTeamResponse(hackathon.getId(), hackathon.getEventName(), team.getId(), team.getName(), participants, team.getPaymentDone(),
			team.getScore(), team.getSubmissionURL(), team.getTeamLead().getId());
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

}
