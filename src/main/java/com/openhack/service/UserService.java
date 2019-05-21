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
import com.openhack.contract.UserResponse;
import com.openhack.dao.HackathonDao;
import com.openhack.dao.OrganizationDao;
import com.openhack.dao.ParticipantDao;
import com.openhack.dao.UserDao;
import com.openhack.domain.Address;
import com.openhack.domain.Hackathon;
import com.openhack.domain.Organization;
import com.openhack.domain.Participant;
import com.openhack.domain.Team;
import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
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
	
	@Autowired
	private HackathonDao hackathonDao;
	
	@Autowired
	private ParticipantDao participantDao;
	
	@Autowired ErrorResponse errorResponse;
	@Autowired UserResponse response;
	
	@Transactional(readOnly=true)
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
			String aboutMe = (String) payload.get("aboutMe");

			userProfile2 = userDao.findByEmail(email);
			
			if (userProfile2 != null)
				if (! userProfile.getEmail().equalsIgnoreCase(userProfile2.getEmail()))
					throw new DuplicateException("User", "email", email);			

			if (screenname != null) {
				if (screenname.length() < 3)
					throw new InvalidArgumentException("Screenname cannot be less than 3 characters");

				userProfile2 = userDao.findByScreenname(screenname);		

				if (userProfile2 != userProfile)
					throw new DuplicateException("User", "screenname", screenname);			
			} else {
				throw new InvalidArgumentException("Screenname cannot be null");

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
			userProfile.setAboutMe(aboutMe);
			
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
				
				String city = null;
				String state = null;
				String zip = null;
				String street = null;
				
				Address address = user.getAddress();
				
				if (address != null) {
					city = address.getCity();
					state = address.getState();
					zip = address.getZip();
					street = address.getStreet();			
				}
				
				response = new UserResponse(
						user.getId(),
						user.getFirstName(), 
						user.getLastName(),
						user.getEmail(),
						user.getTitle(),
						city,
						state,
						street,
						zip,
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
		
		public ResponseEntity<?> approveRequest(UserResponse request){
			
			try {
				
				if(request == null)
					throw new InvalidArgumentException("User in MemberRequest is Null");
				
				UserProfile user = userDao.findById(request.getId());
				if(user == null)
					throw new NotFoundException("User", "Id", request.getId());
				
//				if(request.getOrganization() == null)
//					throw new InvalidArgumentException("Organization in MemberRequest is Null");
				
//				Organization organization = organizationDao.findByOrganizationName(request.getOrganization().getName());
//				if(organization == null)
//					throw new NotFoundException("Orgnization", "Name", request.getOrganization().getName());
				
				Organization organization = user.getOrganization();
				if(organization == null)
					throw new InvalidArgumentException("Organization is null");
				
				user.setMembershipStatus("Approved");

				String city = null;
				String state = null;
				String zip = null;
				String street = null;
				
				Address address = user.getAddress();
				
				if (address != null) {
					city = address.getCity();
					state = address.getState();
					zip = address.getZip();
					street = address.getStreet();			
				}
				
				response = new UserResponse(
						user.getId(),
						user.getFirstName(), 
						user.getLastName(),
						user.getEmail(),
						user.getTitle(),
						city,
						state,
						street,
						zip,
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
				

				String city = null;
				String state = null;
				String zip = null;
				String street = null;
				
				Address address = member.getAddress();
				
				if (address != null) {
					city = address.getCity();
					state = address.getState();
					zip = address.getZip();
					street = address.getStreet();			
				}
				
				response = new UserResponse(
						member.getId(),
						member.getFirstName(), 
						member.getLastName(),
						member.getEmail(),
						member.getTitle(),
						city,
						state,
						street,
						zip,
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
		public ResponseEntity<?> rejectRequest(UserResponse requester){
			
			try {
				UserProfile user = userDao.findById(requester.getId());
				
				if(user == null)
					throw new NotFoundException("User", "Id", requester.getId());
				
				user.setOrganization(null);
				
				user.setMembershipStatus("NA");
				
				String orgName = null;
				
				String city = null;
				String state = null;
				String zip = null;
				String street = null;
				
				Address address = user.getAddress();
				
				if (address != null) {
					city = address.getCity();
					state = address.getState();
					zip = address.getZip();
					street = address.getStreet();			
				}
				
				response = new UserResponse(
						user.getId(),
						user.getFirstName(), 
						user.getLastName(),
						user.getEmail(),
						user.getTitle(),
						city,
						state,
						street,
						zip,
						user.getPotraitURL(),
						user.getAboutMe(),
						user.getScreenName(),
						orgName, 
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
		
		@Transactional(readOnly=true)
		public ResponseEntity<?> listHackers() {
			try {
				List<UserResponse> hackersListResponse = new ArrayList<UserResponse>();
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
		
		@Transactional(readOnly=true)
		public ResponseEntity<?> selectListHackers(long id) {
			try {
				Hackathon hackathon = null;
				List<UserResponse> hackersListResponse = new ArrayList<UserResponse>();
				List<UserProfile> hackersList = userDao.listHackers();

				hackathon = hackathonDao.findById(id);

				if(hackathon==null)
					throw new NotFoundException("Hackathon", "Id", id);

				List <UserProfile> judges = hackathon.getJudges();	

				List <Team> teams = participantDao.findTeamsByHackathonId(id);
				boolean is_judge = false;
				boolean is_member = false;
				for(UserProfile hacker : hackersList) {
					is_judge = false;				
					if (judges != null)
						for (int j=0; j<judges.size(); j++) {
							if (hacker.getEmail().equals(judges.get(j).getEmail())) {
								is_judge = true;
								break;
							}
						}		
					if (!is_judge) {	
						if (teams != null) {
							for (int i = 0; i < teams.size(); i++) {
								Team t = teams.get(i);
								List <Participant> members = participantDao.findParticipantsByTeam(t.getId());
								for (int k = 0; k < members.size(); k++) {
									is_member = false;
									Participant p = members.get(k);
									if (p.getUser().getEmail().equals(hacker.getEmail())) {
										is_member = true;
										break;
									}
								}
							}
						}						
						
						if (!is_member) {								
								hackersListResponse.add(new UserResponse(
													hacker.getId(),
													hacker.getFirstname(),
													hacker.getLastname()));
						}
					}
				}
									
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(hackersListResponse);
			}
			catch(Exception e) {
				errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}
		}

		
		@Transactional(readOnly=true)
		public ResponseEntity<?> findPendingMembers(long userId){
			
			List<UserResponse> listOfUsers = new ArrayList<UserResponse>();
			System.out.println("User id : "+userId);
			try {
				
				UserProfile user = userDao.findById(userId);
				
				if(user == null)
					throw new NotFoundException("User", "Id", userId);
				
				
				System.out.println("Getting user owned organizations : ");
				if(user.getOwnedOrganizations().size() != 0) {
					
					System.out.println("Total user owned organizations : "+user.getOwnedOrganizations().size());
					
					for( Organization organization : user.getOwnedOrganizations()) {
	 
						System.out.println("For organization : "+organization.getName()+" org id : "+organization.getId());
						
						for(UserProfile hacker : userDao.findPendingRequests(organization.getId())) {
							
							System.out.println("organization.getId() : "+organization.getId()+" "+hacker.getOrganization().getId());
							
							if(hacker.getOrganization() != null && organization.getId() == hacker.getOrganization().getId())
							{
								System.out.println("For user : "+hacker.getFirstName());
								
								String city = null;
								String state = null;
								String zip = null;
								String street = null;
								
								Address address = hacker.getAddress();
								
								if (address != null) {
									city = address.getCity();
									state = address.getState();
									zip = address.getZip();
									street = address.getStreet();			
								}
								
								String orgName = null;
								
								if(hacker.getOrganization() != null)
								{
									orgName = hacker.getOrganization().getName();
								}
								
								listOfUsers.add(new UserResponse(
										hacker.getId(),
										hacker.getFirstName(), 
										hacker.getLastName(),
										hacker.getEmail(),
										hacker.getTitle(),
										city,
										state,
										street,
										zip,
										hacker.getPotraitURL(),
										hacker.getAboutMe(),
										hacker.getScreenName(),
										orgName,
										hacker.getMembershipStatus()));
								}
							}
						
					}
					System.out.println("List of all users with pending requests"+listOfUsers.toArray());
				}
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(listOfUsers);
			}
			catch(Exception e){
				errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}
		}
}
