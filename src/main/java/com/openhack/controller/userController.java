package com.openhack.controller;

import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.contract.EmptyResponse;
import com.openhack.contract.ErrorResponse;
import com.openhack.service.UserService;

@RestController
@Transactional
@RequestMapping("/")

public class UserController {
	/** The employee service. */
	@Autowired
	private UserService userService;
	
	@Autowired ErrorResponse errorResponse;
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST )
	public String signin(
			@RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {
		
		return userService.signinUser(username, password);
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST )
	public ResponseEntity<?> signup(
			@RequestParam(value = "firstname") String firstname,
			@RequestParam(value = "lastname") String lastname,
			@RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
		
		try {
			return userService.signupUser(firstname,lastname,email,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse = new ErrorResponse("BadRequest", "400", "Invalid e-mail address");
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}			
	}	
}

