package com.openhack.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.Judge;
import com.openhack.contract.MyHackathonResponse;
import com.openhack.contract.OrganizationResponse;
import com.openhack.dao.OrganizationDao;
import com.openhack.dao.UserDao;
import com.openhack.domain.Hackathon;
import com.openhack.domain.Organization;
import com.openhack.domain.UserProfile;
import com.openhack.exception.DuplicateException;
import com.openhack.exception.NotFoundException;

@Service
public class OrganizationService {
	
	@Autowired
	OrganizationDao organizationDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ErrorResponse errorResponse;
	
	@Transactional
	public ResponseEntity<?> store(Organization organization){
		try {
			
			if(organization == null)
				throw new Exception("Organization is null");
			
			OrganizationResponse response = null;
			UserProfile owner = null;
			
			if(organization.getOwner() != null)
				owner = userDao.findById(organization.getOwner().getId());
			
			if(owner == null)
				throw new NotFoundException("Organization", "Owner", organization.getOwner().getId());
			
			if(organizationDao.findByOrganizationName(organization.getName()) != null)
				throw new DuplicateException("Organization", "Name", organization.getName());
			
			organization = organizationDao.store(organization);
			
			System.out.println("Stored Organization");
			List<MyHackathonResponse> hackathonResponse = new ArrayList<MyHackathonResponse>();
			
			// Get all hackathons judged by user.
			if(organization.getHackathons()!= null ){
			for( Hackathon hackathon : organization.getHackathons())
			{
				hackathonResponse.add(new MyHackathonResponse(
						hackathon.getId(), 
						hackathon.getEventName(),
						hackathon.getStartDate(),
						hackathon.getEndDate(),
						hackathon.getDescription(),
						0));
			}
			
			}
			
			System.out.println("Generated myHackathonResponse");
			
			Judge organizationOwner = new Judge(organization.getOwner().getId(), organization.getOwner().getFirstName());
			
			System.out.println("Generated owner");
			
			List<Judge> memberList = new ArrayList<Judge>();
			
			if(organization.getMembers()!= null) {
			
				for(UserProfile user : organization.getMembers())
				{
					memberList.add(new Judge(user.getId(), user.getFirstName()));
				}
			}
			System.out.println("Generated member List");
			
			response = new OrganizationResponse(
					organization.getId(),
					organization.getName(),
					organizationOwner,
					organization.getDescription(), 
					organization.getAddress(),
					memberList,
					hackathonResponse);
			
			System.out.println("Generated final response");
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
			
		}catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		} catch (DuplicateException e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		} catch (Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	@Transactional
	public ResponseEntity<?> listOrganizations() {
		try {
			List<OrganizationResponse> organizationResponse = new ArrayList();
			
			List<Organization> organizationList = organizationDao.listOrganizations();
			for(Organization organization : organizationList) {
				
			List<MyHackathonResponse> hackathonResponse = new ArrayList<MyHackathonResponse>();
			if(organization.getHackathons()!= null ){
			for( Hackathon hackathon : organization.getHackathons())
			{
				hackathonResponse.add(new MyHackathonResponse(
						hackathon.getId(), 
						hackathon.getEventName(),
						hackathon.getStartDate(),
						hackathon.getEndDate(),
						hackathon.getDescription(),
						0));
			}
			
			}
			
			System.out.println("Generated myHackathonResponse");
			
			Judge organizationOwner = new Judge(organization.getOwner().getId(), organization.getOwner().getFirstName());
			
			System.out.println("Generated owner");
			
			List<Judge> memberList = new ArrayList<Judge>();
			
			if(organization.getMembers()!= null) {
			
				for(UserProfile user : organization.getMembers())
				{
					memberList.add(new Judge(user.getId(), user.getFirstName()));
				}
			}
			System.out.println("Generated member List");
			
			organizationResponse.add(new OrganizationResponse(
					organization.getId(),
					organization.getName(),
					organizationOwner,
					organization.getDescription(), 
					organization.getAddress(),
					memberList,
					hackathonResponse));
			}
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(organizationResponse);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	@Transactional
	public ResponseEntity<?> findById(long id) {
		
		try {
		Organization organization = organizationDao.findById(id);
		
		if(organization == null)
			throw new NotFoundException("Organization", "ID", id);

		Judge organizationOwner = new Judge(organization.getOwner().getId(), organization.getOwner().getFirstName());
		
		List<MyHackathonResponse> hackathonResponse = new ArrayList<MyHackathonResponse>();
		List<Judge> memberList = new ArrayList<Judge>();
		
		if(organization.getMembers()!= null) {
		
			for(UserProfile user : organization.getMembers())
			{
				memberList.add(new Judge(user.getId(), user.getFirstName()));
			}
		}
		
		if(organization.getHackathons()!= null )
		{
			for( Hackathon hackathon : organization.getHackathons())
			{
				hackathonResponse.add(new MyHackathonResponse(
						hackathon.getId(), 
						hackathon.getEventName(),
						hackathon.getStartDate(),
						hackathon.getEndDate(),
						hackathon.getDescription(),
						0));
			}
			
		}
		
		OrganizationResponse organizationResponse = new OrganizationResponse(
				organization.getId(),
				organization.getName(),
				organizationOwner,
				organization.getDescription(),
				organization.getAddress(),
				memberList,
				hackathonResponse
				);
		
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(organizationResponse);
	}
	catch(NotFoundException e) {
		errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
	}catch(Exception e) {
		errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
	}
		
	}
}
