package com.openhack.service;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.UserResponse;
import com.openhack.dao.UserDao;
import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
import com.openhack.exception.DuplicateException;

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
	public ResponseEntity<?>  signinUser(String email, String password) throws Exception {
		UserProfile userProfile=null;
		userProfile = userDao.findByEmail(email);
		if (userProfile!=null) {
			response = new UserResponse(email);	
		}
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@Transactional
	public ResponseEntity<?>  signupUser(String firstname, String lastname, String email, String password) throws Exception {
		try {
			UserProfile userProfile=null;
			UserAccount userAccount=null;
	
			if (!isEmailValid(email)) { // if email is invalid 
				errorResponse = new ErrorResponse("BadRequest", "400", "Invalid e-mail address");
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}		
			// Event name should be unique so if an event with given name already exist, return BadRequest.
			userProfile = userDao.findByEmail(email);
			
			if (userProfile != null)
				throw new DuplicateException("UserProfile", "email", email);
			
			userProfile = new UserProfile(firstname, lastname, email);
			//userAccount = new UserAccount(userProfile,password,0,"Pending Verification");
			
			userProfile = userDao.store(userProfile);
			//userAccount = userDao.store(userAccount);
			String subject = String.format("Open Hackathon Account Verification");
			String text = String.format("Please confirm your registration for hackathon by following the link below. \n %s", 
					"Dummy link");
			emailService.sendSimpleMessage(userProfile.getEmail(), subject , text);
			response = new UserResponse(userProfile.getEmail());					
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
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
