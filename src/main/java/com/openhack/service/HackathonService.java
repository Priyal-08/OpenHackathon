package com.openhack.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.HackathonResponse;
import com.openhack.dao.HackathonDao;
import com.openhack.dao.OrganizationDao;
import com.openhack.dao.UserDao;
import com.openhack.domain.Hackathon;
import com.openhack.domain.Organization;
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
	
	/** The response. */
	@Autowired	
	private HackathonResponse response;
	
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
			int minTeamSize, int maxTeamSize, List<Long> judges, List<Long> sponsors, long discount) {
		Hackathon hackathon=null;
		Date today = new Date();
		try {
			SimpleDateFormat formatter =new SimpleDateFormat("dd/MM/yyyy"); 
			Date startDate = formatter.parse(sDate);
			Date endDate = formatter.parse(eDate);
			// If the event name is null, return BadRequest.
			if(eventName == null || today.compareTo(startDate) >0 || today.compareTo(endDate)<0) {
				throw new InvalidArgumentException("eventName/startDate/endDate");
			} else {
				// Event name should be unique so if an event with given name already exist, return BadRequest.
				hackathon = hackathonDao.findByEventName(eventName);
				if(hackathon!=null)
					throw new DuplicateException("Hackathon", "eventName", eventName);
				List<Organization> sponsorsList = organizationDao.findByIds(sponsors);
				List<UserProfile> judgesList = userDao.findByIds(judges);
				hackathon = new Hackathon(eventName, startDate, endDate, description, fees,
						judgesList, minTeamSize, maxTeamSize, sponsorsList, discount);
				hackathonDao.store(hackathon);
				response = new HackathonResponse();  // TODO: create hackathon response
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
			}
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
	 * Gets the hackathon.
	 *
	 * @param id: the hackathon id
	 * @return ResponseEntity: the hackathon object on success/ error message on error
	 */
	@Transactional
	public ResponseEntity<?> getHackathon(long id) {
		try {
			Hackathon hackathon = hackathonDao.findById(id);
			// If the hackathon with given id does not exist, return NotFound.
			if(hackathon==null)
				throw new NotFoundException("Hackathon", "Id", id);
			response = new HackathonResponse(); // TODO: create hackathon response
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
}
