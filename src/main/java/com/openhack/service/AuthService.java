package com.openhack.service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.AuthResponse;
import com.openhack.contract.UserResponse;
import com.openhack.dao.UserDao;
import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
import com.openhack.domain.UserRole;
import com.openhack.exception.DuplicateException;
import com.openhack.exception.InvalidArgumentException;
import com.openhack.security.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private Environment env;
	
	/** The user dao. */
	@Autowired
	private UserDao userDao;
	
	/** The user dao. */
	@Autowired
    JwtTokenProvider tokenProvider;
	
	@Autowired 
	ErrorResponse errorResponse;
	
	@Autowired 
	AuthResponse authResponse;
	
	@Autowired 
	UserResponse response;
	
	@Transactional
	public ResponseEntity<?>  signinUser(String username, String password, Authentication authentication) throws Exception {
		try {
			UserAccount userAccount=null;
			String role = null;
			userAccount = userDao.findAccountByEmail(username);
			long id = userAccount.getUser().getId();
			UserRole userRole = userDao.findRoleById(id);
			
			if (userRole != null)
				role = userRole.getRole();
	        String jwt = tokenProvider.generateToken(authentication);
			
	        authResponse = new AuthResponse(id, role, userRole.getUser().getFirstName(), userRole.getUser().getScreenName(), jwt);	
	        authResponse.setRole(role);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(authResponse);
			
		} catch (Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			
		}
	}
	
	@Transactional
	public ResponseEntity<?>  signupUser(String firstname, String lastname, String email, String password) throws Exception {
		try {
			UserProfile userProfile=null, userProfile2 = null;
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
			
			if (lastname != null) {
				if (lastname.length() < 3)
					throw new InvalidArgumentException("Screenname cannot be less than 3 characters");	
				
				userProfile2 = userDao.findByScreenname(lastname);		

				if (userProfile2 != null)
					throw new DuplicateException("User", "screenname", lastname);			

			}
			
			userProfile = new UserProfile(firstname, lastname, email);
			
			userProfile = userDao.store(userProfile);
			UUID authcode = UUID.randomUUID();
			
			

			userAccount = new UserAccount(userProfile,password,0,"Pending Verification", authcode.toString(), null);
			userAccount = userDao.store(userAccount);
				
		    String emailDomain = email.substring(email.lastIndexOf("@") + 1).toLowerCase();
		    
		    if(emailDomain.equals("sjsu.edu"))	// add admin role
				userRole = new UserRole(userProfile, "Admin");
			else
				userRole = new UserRole(userProfile, "Hacker");
		    
			userRole = userDao.store(userRole);			
			
			String emailId = userProfile.getEmail();	
			
		    String baseURL = env.getProperty("frontendserver.baseurl");
		    System.out.println("Frontend URL is " + baseURL);

			String verifyURL = "/registration-confirmation/?token=" + authcode.toString();
			String subject = String.format("Open Hackathon Account Verification");
			String text = String.format("Hi %s, \n\nPlease confirm your registration for OpenHackathon by clicking the link below. \n %s\n\n\n Thank you, \n Team OpenHackathon", 
					userProfile.getFirstname(), baseURL + verifyURL);
			
			new Thread(() -> {
				System.out.println("Sending mail to " + emailId );
				emailService.sendSimpleMessage(emailId, subject , text);
			}).start();

						
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
