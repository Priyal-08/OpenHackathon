package com.openhack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.service.ParticipantService;

/**
 * The Class ParticipantController.
 */
@RestController
@RequestMapping("/participant")
public class ParticipantController {
	
	/** The participantService service. */
	@Autowired
	private ParticipantService participantService;
	
	/**
	 * Gets the list of hackathon in which user has registered.
	 *
	 * @param id: the user id
	 * @return ResponseEntity: the hackathon object on success/ error message on error
	 */
	@RequestMapping(value = "/{id}/hackathon", method = RequestMethod.GET)
	public ResponseEntity<?> getHackathon(
			@PathVariable("id") long id) {
		return participantService.getHackathon(id);
	}

}