package com.openhack.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.hash.Hashing;
import com.openhack.contract.ErrorResponse;
import com.openhack.contract.HackathonResponse;
import com.openhack.contract.Judge;
import com.openhack.contract.UserResponse;
import com.openhack.dao.UserDao;
import com.openhack.domain.Address;
import com.openhack.domain.Organization;
import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
import com.openhack.domain.UserRole;
import com.openhack.exception.DuplicateException;
import com.openhack.exception.InvalidArgumentException;
import com.openhack.exception.NotFoundException;

@Service
public class UserService {
	
	@Autowired
	private EmailService emailService;
	
	/** The user dao. */
	@Autowired
	private UserDao userDao;
	
	@Autowired ErrorResponse errorResponse;
	@Autowired UserResponse response;

	@Transactional
	public ResponseEntity<?>  signinUser(String username, String password) throws Exception {
		try {
			UserAccount userAccount=null;
			int attempts = 0;
			final String hashedPassword = Hashing.sha256()
			        .hashString(password, StandardCharsets.UTF_8)
			        .toString();
			userAccount = userDao.findByUserAndPassword(username,hashedPassword);
			
			if (userAccount==null) {
				errorResponse = new ErrorResponse("BadRequest", "400","Invalid username/password");
				//attempts = userAccount.getAttempts();
				//userAccount.setAttempts(attempts+1);
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
				
			}
			
			response = new UserResponse(userAccount.getStatus());					
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
			
		} catch (Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			
		}
	}
	
	@Transactional
	public ResponseEntity<?>  signupUser(String firstname, String lastname, String email, String password) throws Exception {
		try {
			UserProfile userProfile=null;
			UserAccount userAccount=null;
			UserRole userRole= null;
	
			if (!validateEmail(email)) { // if email is invalid 
				errorResponse = new ErrorResponse("BadRequest", "400", "Invalid e-mail address");
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}		
			// Event name should be unique so if an event with given name already exist, return BadRequest.
			userProfile = userDao.findByEmail(email);
			
			if (userProfile != null)
				throw new DuplicateException("UserProfile", "email", email);
	
			userProfile = new UserProfile(firstname, lastname, email);
			
			userProfile = userDao.store(userProfile);
			UUID authcode = UUID.randomUUID();
			
			final String hashedPassword = Hashing.sha256()
			        .hashString(password, StandardCharsets.UTF_8)
			        .toString();

			userAccount = new UserAccount(userProfile,hashedPassword,0,"Pending Verification", authcode.toString());
			userAccount = userDao.store(userAccount);
				
		    String emailDomain = email.substring(email.lastIndexOf("@") + 1).toLowerCase();
		    
		    if(emailDomain.equals("sjsu.edu"))	// add admin role
				userRole = new UserRole(userProfile, "Admin");
			else
				userRole = new UserRole(userProfile, "Hacker");
		    
			userRole = userDao.store(userRole);			
			
			String baseURL = "http://localhost:5000";
			String verifyURL = "/verify/" + authcode.toString();
			String subject = String.format("Open Hackathon Account Verification");
			String text = String.format("Please confirm your registration for hackathon by following the link below. \n %s", 
					baseURL + verifyURL);
			emailService.sendSimpleMessage(userProfile.getEmail(), subject , text);
			response = new UserResponse(userProfile.getEmail());					
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
	@Transactional
	public ResponseEntity<?> verify(String authcode) {
		try {
			UserAccount userAccount=null;
			userAccount = userDao.findByAuthCode(authcode);
			
			if (userAccount == null) {	// if auth code not found in DB
				errorResponse = new ErrorResponse("BadRequest", "400", "Invalid authcode");
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}		
			userAccount.setStatus("Active");
			
			response = new UserResponse(userAccount.getStatus());					
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
	}
	
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
					orgName);
					
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
					orgName);

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

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		public static boolean validateEmail(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		        return matcher.find();
		}
		
		
		public static boolean isEmailValid(String email) 
	    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                            "[a-zA-Z0-9_+&*-]+)*@" + 
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                            "A-Z]{2,7}$"; 
	        Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return pat.matcher(email).matches(); 
	    } 	
}
