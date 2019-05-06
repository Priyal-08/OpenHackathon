package com.openhack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.domain.Organization;
import com.openhack.service.OrganizationService;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;
	
	@PostMapping("/create")
	public ResponseEntity<?> store(@RequestBody Organization organization)
	{
		return organizationService.store(organization);
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
	
//	@PostMapping("/join/{id}")
//	public ResponseEntity<?> join(@RequestBody Organization organization)
//	{
//		return organizationService.store(organization);
//	}
}
