package com.openhack.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.contract.HackathonRequest;
import com.openhack.domain.Organization;
import com.openhack.service.HackathonService;

/**
 * The Class HackathonController.
 */
@CrossOrigin
@RestController
@RequestMapping("/hackathon")
public class HackathonController {

	/** The hackathon service. */
	@Autowired
	private HackathonService hackathonService;
	
	/**
	 * Creates the hackathon.
	 * @return ResponseEntity: newly created hackathon object on success/ error message on error
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	
	public ResponseEntity<?> createHackathon(@RequestBody HackathonRequest hackathonRequest) {
		String eventName = hackathonRequest.getEventName();
		String description = hackathonRequest.getDescription();
		String startDate = hackathonRequest.getStartDate();
		String endDate = hackathonRequest.getEndDate();
		long fees = hackathonRequest.getFees();
		int minTeamSize = hackathonRequest.getMinTeamSize();
		int maxTeamSize =  hackathonRequest.getMaxTeamSize();
		List<Long> judges = hackathonRequest.getJudges();
		List<Long> sponsors = hackathonRequest.getSponsors();
		float discount = hackathonRequest.getDiscount();
		return hackathonService.createHackathon(eventName, description, startDate, endDate, fees,
				minTeamSize, maxTeamSize, judges, sponsors, discount);
	}
	
	/**
	 * Gets all hackathons.
	 *
	 * @return ResponseEntity: list of hackathon objects on success/ error message on error
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getHackathons(){
		return hackathonService.getHackathons();
	}
	
	/**
	 * Gets the hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: the hackathon object on success/ error message on error
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getHackathon(
			@PathVariable("id") long id, @RequestParam("userId") long userId) {
		return hackathonService.getHackathon(id, userId);
	}
	
	@RequestMapping(value = "/{id}/financial_report", method = RequestMethod.GET)
	public ResponseEntity<?> getFinancialReport(
			@PathVariable("id") long id) {
		return hackathonService.getFinancialReport(id);
	}
	
	@RequestMapping(value = "/{id}/leaderboard", method = RequestMethod.GET)
	public ResponseEntity<?> getLeaderboard(
			@PathVariable("id") long id) {
		return hackathonService.getLeaderboard(id);
	}
	
	@RequestMapping(value = "/{id}/paymentreport", method = RequestMethod.GET)
	public ResponseEntity<?> getPaymentReport(
			@PathVariable("id") long id) {
		return hackathonService.getPaymentReport(id);
	}
	
	/**
	 * Updates the hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: hackathon object on success/ error message on error
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateHackathon(
			@PathVariable("id") long id,
			@RequestBody HackathonRequest hackathonRequest) {

		String eventName = hackathonRequest.getEventName();
		String description = hackathonRequest.getDescription();
		String startDate = hackathonRequest.getStartDate();
		String endDate = hackathonRequest.getEndDate();
		long fees = hackathonRequest.getFees();
		int minTeamSize = hackathonRequest.getMinTeamSize();
		int maxTeamSize =  hackathonRequest.getMaxTeamSize();
		List<Long> judges = hackathonRequest.getJudges();
		List<Long> sponsors = hackathonRequest.getSponsors();
		float discount = hackathonRequest.getDiscount();

		return hackathonService.updateHackathon(id, eventName, description, startDate, endDate, fees,
				minTeamSize, maxTeamSize, judges, sponsors, discount);
	}
	
	/**
	 * Updates the hackathon status.
	 *
	 * @param id: the hackathon id
	 * @param status: hackathon status
	 * @return ResponseEntity: updated hackathon object on success/ error message on error
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateHackathonStatus(
			@PathVariable("id") long id,
			@RequestParam("status") int status) {
		return hackathonService.updateHackathonStatus(id, status);
	}
	
	/**
	 * Deletes a hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: deleted hackathon object on success/ error message on error
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteHackathon(
			@PathVariable("id") long id) {
		return hackathonService.deleteHackathon(id);
	}
}
