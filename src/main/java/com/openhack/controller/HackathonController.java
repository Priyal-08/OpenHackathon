package com.openhack.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;
import com.openhack.contract.ErrorResponse;
import com.openhack.contract.ExpenseRequest;
import com.openhack.contract.HackathonRequest;
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
	
	/** Error response */
	@Autowired ErrorResponse errorResponse;
	
	/**
	 * Creates the hackathon.
	 * @return ResponseEntity: newly created hackathon object on success/ error message on error
	 */
	@RequestMapping(method = RequestMethod.POST)
	
	public ResponseEntity<?> createHackathon(@RequestBody HackathonRequest hackathonRequest) {
		try {
			String eventName = hackathonRequest.getEventName();
			String description = hackathonRequest.getDescription();
			String startDate = hackathonRequest.getStartDate();
			String endDate = hackathonRequest.getEndDate();
			float fees = hackathonRequest.getFees();
			int minTeamSize = hackathonRequest.getMinTeamSize();
			int maxTeamSize =  hackathonRequest.getMaxTeamSize();
			List<Long> judges = hackathonRequest.getJudges();
			List<Long> sponsors = hackathonRequest.getSponsors();
			float discount = hackathonRequest.getDiscount();
			return hackathonService.createHackathon(eventName, description, startDate, endDate, fees,
					minTeamSize, maxTeamSize, judges, sponsors, discount);
		
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
	
	@RequestMapping(value = "/{id}/earningreport", method = RequestMethod.GET)
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
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateHackathon(
			@PathVariable("id") long id,
			@RequestBody HackathonRequest hackathonRequest) {

		try {
			String eventName = hackathonRequest.getEventName();
			String description = hackathonRequest.getDescription();
			String startDate = hackathonRequest.getStartDate();
			String endDate = hackathonRequest.getEndDate();
			float fees = hackathonRequest.getFees();
			int minTeamSize = hackathonRequest.getMinTeamSize();
			int maxTeamSize =  hackathonRequest.getMaxTeamSize();
			List<Long> judges = hackathonRequest.getJudges();
			List<Long> sponsors = hackathonRequest.getSponsors();
			float discount = hackathonRequest.getDiscount();

			return hackathonService.updateHackathon(id, eventName, description, startDate, endDate, fees,
					minTeamSize, maxTeamSize, judges, sponsors, discount);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
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
		try {
			ResponseEntity<?> resp =  hackathonService.updateHackathonStatus(id, status);
			return resp;
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
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
		
		try {
			return hackathonService.deleteHackathon(id);
		} 
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}

	}
	
	/**
	 * Add new expense to hackathon.
	 * @return ResponseEntity: success/error response
	 */
	@RequestMapping(value="{id}/expense", method = RequestMethod.POST)
	
	public ResponseEntity<?> AddExpense(@PathVariable("id") long id,
			@RequestBody ExpenseRequest expenseRequest) {
		try {
		String title = expenseRequest.getTitle();
		String description = expenseRequest.getDescription();
		String expenseDate = expenseRequest.getExpenseDate();
		float amount = expenseRequest.getAmount();
		return hackathonService.addExpense(id, title, description, expenseDate, amount);
		}		
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}

	
	/**
	 * Get expenses for hackathon.
	 * @return ResponseEntity: expense list on success/ error message on error
	 */
	@RequestMapping(value="{id}/expense", method = RequestMethod.GET)
	
	public ResponseEntity<?> getExpenseList(@PathVariable("id") long id) {
		return hackathonService.getExpenseList(id);
	}
}
