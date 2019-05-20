package com.openhack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.domain.Organization;
import com.openhack.service.OrganizationService;

@RestController
@RequestMapping("/organization")
@CrossOrigin
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;
	
	@PostMapping("/create")
	public ResponseEntity<?> store(@RequestBody Organization organization)
	{
		System.out.println("====================Create orgnization====================");
		System.out.println(organization.getName());
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
	
//	@PostMapping("/join/{id}")
//	public ResponseEntity<?> join(@RequestBody Organization organization)
//	{
//		return organizationService.store(organization);
//	}
}
