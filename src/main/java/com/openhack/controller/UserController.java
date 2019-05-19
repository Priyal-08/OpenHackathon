package com.openhack.controller;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.MemberRequest;
import com.openhack.contract.UserResponse;
import com.openhack.domain.UserProfile;
import com.openhack.service.UserService;

@RestController
@Transactional
@RequestMapping("/")
@CrossOrigin

public class UserController {
	/** The employee service. */
	@Autowired
	private UserService userService;
	
	@Autowired ErrorResponse errorResponse;
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET )
	public ResponseEntity<?> getUserProfile(
			@PathVariable("id") long id) {
			return userService.getUserProfile(id);
		}	

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT )
	public ResponseEntity<?> updateUserProfile(
		   @PathVariable("id") long id,
		   @RequestBody Map<String, Object> payload) {
		  System.out.println(payload);

		try {
			return userService.updateUserProfile(id,payload); // firstname,lastname,city, state, street, zip, email, potrait_url, screenname, title, name, orgid);
			//return userService.getUserProfile(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse = new ErrorResponse("BadRequest", "400", "Invalid e-mail address");
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}			
	}	

	@RequestMapping(value = "/user/{id}/join/{orgName}", method = RequestMethod.GET )
	public ResponseEntity<?> joinOrganization(
			@PathVariable("id") long id,
			@PathVariable("orgName") String orgName) {
			return userService.joinOrganization(id, orgName);
		}	

	@RequestMapping(value = "/user/approve", method = RequestMethod.POST )
	public ResponseEntity<?> approveOrganization(
			@RequestBody UserResponse request) {
			return userService.approveRequest(request);
		}	

	@RequestMapping(value = "/user/leave", method = RequestMethod.POST )
	public ResponseEntity<?> leaveOrganization(
			@RequestBody UserResponse user) {
			return userService.leaveOrganization(user);
		}	
	
	/**
	 * Gets all organizations.
	 *
	 * @return ResponseEntity: list of organization objects on success/ error message on error
	 */
	@RequestMapping(value = "/user/list",method = RequestMethod.GET)
	public ResponseEntity<?> listHackers(){
		return userService.listHackers();
	}
	
	@RequestMapping(value = "/user/{id}/pendingrequests", method = RequestMethod.GET )
	public ResponseEntity<?> findPendingRequests(
			@PathVariable("id") long id) {
			return userService.findPendingMembers(id);
		}

}


