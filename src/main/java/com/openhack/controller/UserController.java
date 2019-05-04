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
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST )
	public ResponseEntity<?> signin( @RequestBody Map<String, Object> payload){
		 System.out.println(payload);
		 String username = (String) payload.get("username");
		 String password = (String) payload.get("password");
		 try {
			return userService.signinUser(username, password);
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				errorResponse = new ErrorResponse("BadRequest", "400", "Invalid username/password.");
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST )
	public ResponseEntity<?> signup( @RequestBody Map<String, Object> payload) {
		  System.out.println(payload);
		  String firstname = (String) payload.get("firstname");
		  String lastname = (String) payload.get("lastname");
		  String email = (String) payload.get("email");
		  String password = (String) payload.get("password");
		try {
			return userService.signupUser(firstname,lastname,email,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse = new ErrorResponse("BadRequest", "400", "Invalid e-mail address");
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}			
	}		
	
	@RequestMapping(value = "/verify/{authcode}", method = RequestMethod.GET )
	public ResponseEntity<?> verify(
			@PathVariable("authcode") String authcode) {
			return userService.verify(authcode);
		}	
}

