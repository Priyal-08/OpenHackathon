package com.openhack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.contract.ErrorResponse;
import com.openhack.domain.Organization;
import com.openhack.service.OrganizationService;

@RestController
@RequestMapping("/organization")
@CrossOrigin
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;
	
	/** Error response */
	@Autowired ErrorResponse errorResponse;

	
	@PostMapping("/create")
	public ResponseEntity<?> store(@RequestBody Organization organization)
	{
		try {
			System.out.println("====================Create orgnization====================");
			System.out.println(organization.getName());
			return organizationService.store(organization);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	/**
	 * Gets all organizations.
	 *
	 * @return ResponseEntity: list of organization objects on success/ error message on error
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listOrganizations(){
		return organizationService.listOrganizations();
	}
	
	/**
	 * Gets organization.
	 *
	 * @return ResponseEntity: list of organization objects on success/ error message on error
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrganizations(
			@PathVariable("id") long id){
		return organizationService.findById(id);
	}
	
}
