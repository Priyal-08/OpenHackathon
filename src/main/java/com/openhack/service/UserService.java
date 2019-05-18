package com.openhack.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.openhack.contract.ErrorResponse;
import com.openhack.contract.MemberRequest;
import com.openhack.contract.UserResponse;
import com.openhack.dao.UserDao;
import com.openhack.domain.Address;
import com.openhack.domain.Organization;
import com.openhack.domain.UserProfile;
import com.openhack.dao.OrganizationDao;
import com.openhack.exception.DuplicateException;
import com.openhack.exception.InvalidArgumentException;
import com.openhack.exception.NotFoundException;

@Service
public class UserService {
	
	/** The user dao. */
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired ErrorResponse errorResponse;
	@Autowired UserResponse response;
	
	@Transactional
	public ResponseEntity<?> getUserProfile(long id) {
		try {
			UserProfile userProfile=null;
			userProfile = userDao.findById(id);
			
			if(userProfile==null)
				throw new NotFoundException("User", "Id", id);

			Address address = userProfile.getAddress();
			
			String city = null;
			String state = null;
			String zip = null;
			String street = null;
			String role = null;
			
			if (address != null) {
				city = address.getCity();
				state = address.getState();
				zip = address.getZip();
				street = address.getStreet();			
			}

			Organization org = userProfile.getOrganization();
			String orgName = null;
			if (org != null) {
				orgName = org.getName();
			}
			
			response = new UserResponse(
					userProfile.getId(),
					userProfile.getFirstName(), 
					userProfile.getLastName(),
					userProfile.getEmail(),
					userProfile.getTitle(),
					city,
					state,
					street,
					zip,
					userProfile.getPotraitURL(),
					userProfile.getAboutMe(),
					userProfile.getScreenName(),
					orgName,
					userProfile.getMembershipStatus()
					);
					
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	@Transactional
	public ResponseEntity<?> updateUserProfile(long id, Map<String, Object> payload) {
		try {
			UserProfile userProfile=null, userProfile2=null;
			userProfile = userDao.findById(id);
			
			if(userProfile==null)
				throw new NotFoundException("User", "Id", id);
			
			String firstname = (String) payload.get("firstname");
			String lastname = (String) payload.get("lastname");
			String city = (String) payload.get("city");
			String state = (String) payload.get("state");
			String street = (String) payload.get("street");
			String zip = (String) payload.get("zip");
			String email = (String) payload.get("email");
			String potraitURL = (String) payload.get("potrait_url");
			String screenname = (String) payload.get("screenname");
			String title = (String) payload.get("title");
			String role = null;
			
			userProfile2 = userDao.findByEmail(email);
			
			if (userProfile2 != null)
				if (! userProfile.getEmail().equalsIgnoreCase(userProfile2.getEmail()))
					throw new DuplicateException("User", "email", email);			

			if (screenname != null) {
				if (screenname.length() < 3)
					throw new InvalidArgumentException("Screenname cannot be less than 3 characters");	

				userProfile2 = userDao.findByScreenname(screenname);		

				if (userProfile2 != null)
					throw new DuplicateException("User", "screenname", screenname);			
			}	
			userProfile.setFirstName(firstname);
			userProfile.setLastName(lastname);
			
			if (city != null || state != null || street != null || zip != null) {
				Address address = userProfile.getAddress();
				if (address == null)
					address = new Address(street, city, state, zip);
				else {
					address.setCity(city);
					address.setState(state);
					address.setStreet(street);
					address.setZip(zip);
				}
				userProfile.setAddress(address);
			}

			userProfile.setEmail(email);
			userProfile.setPotraitURL(potraitURL);
			userProfile.setScreenName(screenname);
			userProfile.setTitle(title);
			
			Organization org = userProfile.getOrganization();
			String orgName = null;
			if (org != null) {
				orgName = org.getName();
			}

			response = new UserResponse(
					userProfile.getId(),
					userProfile.getFirstName(), 
					userProfile.getLastName(),
					userProfile.getEmail(),
					userProfile.getTitle(),
					city,
					state,
					street,
					zip,
					userProfile.getPotraitURL(),
					userProfile.getAboutMe(),
					userProfile.getScreenName(),
					orgName,
					userProfile.getMembershipStatus());

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(NotFoundException e) {
			errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}

	}
		public ResponseEntity<?> joinOrganization(long userId, String organizationName){
			
			try {
				UserProfile user = userDao.findById(userId);
				
				if(user == null)
					throw new NotFoundException("User", "Id", userId);
				
				Organization organization = organizationDao.findByOrganizationName(organizationName);
				if(organization == null)
					throw new NotFoundException("Orgnization", "Name", organizationName);
				
				user.setOrganization(organization);
				
				user.setMembershipStatus("requested");
				
				response = new UserResponse(
						user.getId(),
						user.getFirstName(), 
						user.getLastName(),
						user.getEmail(),
						user.getTitle(),
						user.getAddress().getCity(),
						user.getAddress().getState(),
						user.getAddress().getStreet(),
						user.getAddress().getZip(),
						user.getPotraitURL(),
						user.getAboutMe(),
						user.getScreenName(),
						user.getOrganization().getName(),
						user.getMembershipStatus());

				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
				
			}catch(NotFoundException e) {
				errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}catch(Exception e) {
				errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}
		}
		
		public ResponseEntity<?> approveRequest(MemberRequest request){
			
			try {
				
				if(request.getUser() == null)
					throw new InvalidArgumentException("User in MemberRequest is Null");
				
				UserProfile user = userDao.findById(request.getUser().getUserId());
				if(user == null)
					throw new NotFoundException("User", "Id", request.getUser().getUserId());
				
				if(request.getOrganization() == null)
					throw new InvalidArgumentException("Organization in MemberRequest is Null");
				
				Organization organization = organizationDao.findByOrganizationName(request.getOrganization().getName());
				if(organization == null)
					throw new NotFoundException("Orgnization", "Name", request.getOrganization().getName());
				
				user.setOrganization(organization);
				
				user.setMembershipStatus("Approved");
				
				response = new UserResponse(
						user.getId(),
						user.getFirstName(), 
						user.getLastName(),
						user.getEmail(),
						user.getTitle(),
						user.getAddress().getCity(),
						user.getAddress().getState(),
						user.getAddress().getStreet(),
						user.getAddress().getZip(),
						user.getPotraitURL(),
						user.getAboutMe(),
						user.getScreenName(),
						user.getOrganization().getName(),
						user.getMembershipStatus());

				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
				
			}catch(NotFoundException e) {
				errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}catch(Exception e) {
				errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}
		}
		
		public ResponseEntity<?> leaveOrganization(UserResponse user){
			
			try {
				UserProfile member = userDao.findById(user.getId());
				
				if(member == null)
					throw new NotFoundException("User", "Id", user.getId());
				
				Organization organization = organizationDao.findByOrganizationName(user.getOrganizationName());
				if(organization == null)
					throw new NotFoundException("Orgnization", "Name", user.getOrganizationName());
				
				member.setOrganization(null);
				
				member.setMembershipStatus("NA");
				String orgName = null;
				if(member.getOrganization() != null)
					orgName = member.getOrganization().getName();
				
				response = new UserResponse(
						member.getId(),
						member.getFirstName(), 
						member.getLastName(),
						member.getEmail(),
						member.getTitle(),
						member.getAddress().getCity(),
						member.getAddress().getState(),
						member.getAddress().getStreet(),
						member.getAddress().getZip(),
						member.getPotraitURL(),
						member.getAboutMe(),
						member.getScreenName(),
						orgName, // Ask what is the default value of organization
						member.getMembershipStatus());

				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
				
			}catch(NotFoundException e) {
				errorResponse = new ErrorResponse("NotFound", "404", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}catch(Exception e) {
				errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}
		}
		
		@Transactional
		public ResponseEntity<?> listHackers() {
			try {
				List<UserResponse> hackersListResponse = new ArrayList();
				List<UserProfile> hackersList = userDao.listHackers();
				
				for(UserProfile hacker : hackersList) {
					hackersListResponse.add(new UserResponse(
							hacker.getId(),
							hacker.getFirstname(),
							hacker.getLastname()));
				}
				
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(hackersListResponse);
			}
			catch(Exception e) {
				errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}
		}
	
}
