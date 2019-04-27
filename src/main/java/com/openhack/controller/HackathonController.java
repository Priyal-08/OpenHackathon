package com.openhack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.service.HackathonService;

/**
 * The Class HackathonController.
 */
@RestController
@RequestMapping("/hackathon")
public class HackathonController {

	/** The hackathon service. */
	@Autowired
	private HackathonService hackathonService;
	
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
	 * @return ResponseEntity: newly created hackathon object on success/ error message on error
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createHackathon(
			@RequestParam("eventName") String eventName,
            @RequestParam(value="description", required=false) String description,
            @RequestParam(value="startDate") String startDate,
            @RequestParam(value="endDate") String endDate,
            @RequestParam(value="fees") long fees,
            @RequestParam(value="minTeamSize") int minTeamSize,
            @RequestParam(value="maxTeamSize") int maxTeamSize,
            @RequestParam(value="judges", required=false) List<Long> judges,
            @RequestParam(value="sponsors", required=false) List<Long> sponsors,
            @RequestParam(value="discount", required=false) long discount) {
		return hackathonService.createHackathon(eventName, description, startDate, endDate, fees,
				minTeamSize, maxTeamSize, judges, sponsors, discount);
	}
	
	/**
	 * Gets the hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: the hackathon object on success/ error message on error
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getHackathon(
			@PathVariable("id") long id) {
		return hackathonService.getHackathon(id);
	}
}
