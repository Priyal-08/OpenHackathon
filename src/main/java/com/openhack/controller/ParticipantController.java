package com.openhack.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.ParticipantDetail;
import com.openhack.contract.TeamRegistrationRequest;
import com.openhack.service.ParticipantService;

/**
 * The Class ParticipantController.
 */
@CrossOrigin
@RestController
@RequestMapping("/participant")
public class ParticipantController {

	/** The participantService service. */
	@Autowired
	private ParticipantService participantService;
	@Autowired
	ErrorResponse errorResponse;

	/**
	 * Gets the list of hackathon in which user has registered.
	 *
	 * @param id: the user id
	 * @return ResponseEntity: the hackathon object on success/ error message on
	 *         error
	 */
	@RequestMapping(value = "/{id}/hackathon", method = RequestMethod.GET)
	public ResponseEntity<?> getHackathonList(@PathVariable("id") long id) {
		return participantService.getHackathonList(id);
	}

	/**
	 * Get the hackathon details for given hackathon and user id.
	 *
	 * @param userId: the user id
	 * @param hackathonId: the hackathonId id
	 * @return ResponseEntity: the hackathon object on success/ error message on
	 *         error
	 */
	@RequestMapping(value = "/{userId}/hackathon/{hackathonId}", method = RequestMethod.GET)
	public ResponseEntity<?> getHackathonDetails(@PathVariable("userId") long userId,
			@PathVariable("hackathonId") long hackathonId) {
		return participantService.getHackathonDetails(userId, hackathonId);
	}
	
	/**
	 * Get the team details for given hackathon and user id.
	 *
	 * @param userId: the user id
	 * @param hackathonId: the hackathonId id
	 * @return ResponseEntity: the hackathon object on success/ error message on
	 *         error
	 */
	@RequestMapping(value = "/{userId}/team/{teamId}", method = RequestMethod.GET)
	public ResponseEntity<?> getTeamDetails(@PathVariable("userId") long userId,
			@PathVariable("teamId") long teamId) {
		return participantService.getTeamDetails(userId, teamId);
	}

	/**
	 * Register team for hackathon
	 * @param userId: the user id
	 * @param hackathonId: the hackathonId id
	 * @param members: team members
	 * @return ResponseEntity: the hackathon details object on success/ error
	 *         message on error
	 */
	@RequestMapping(value = "/{userId}/hackathon/{hackathonId}", method = RequestMethod.POST)
	public ResponseEntity<?> registerTeam(@PathVariable("userId") long userId,
			@PathVariable("hackathonId") long hackathonId,
			@RequestBody TeamRegistrationRequest teamRegistrationRequest) {
		try {
			System.out.println("Team Request::" + teamRegistrationRequest);
			String teamName = teamRegistrationRequest.getTeamName();
			List<ParticipantDetail> members = teamRegistrationRequest.getParticipants();
			return participantService.registerTeam(userId, hackathonId, teamName, members);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}

	}

	/**
	 * Updates the code submission URL .
	 *
	 * @param id: the hackathon id
	 * @param status: hackathon status
	 * @return ResponseEntity: updated hackathon object on success/ error message on
	 *         error
	 */
	@RequestMapping(value = "/{id}/hackathon/{hackathonId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateDetails(@PathVariable("id") long id, @PathVariable("hackathonId") long hackathonId,
			@RequestParam(value = "submission_url", required = false) String submissionURL,
			@RequestParam(value = "judgeScore", required = false) String score) {

		try {
			return participantService.updateDetails(id, hackathonId, submissionURL, score);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}


	}

	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public ResponseEntity<?> pay(@RequestBody Map<String, Object> payload) {
		System.out.println(payload);
		String authcode = (String) payload.get("token");
		try {
			return participantService.pay(authcode);
		} catch (Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", "Invalid token");
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}

}
