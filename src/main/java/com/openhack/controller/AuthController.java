package com.openhack.controller;

import com.openhack.contract.ErrorResponse;
import com.openhack.security.JwtTokenProvider;
import com.openhack.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired ErrorResponse errorResponse;

    @RequestMapping(value = "/signin", method = RequestMethod.POST )
	public ResponseEntity<?> authenticateUser( @RequestBody Map<String, Object> payload){
		 System.out.println(payload);
		 String username = (String) payload.get("username");
		 String password = (String) payload.get("password");
		 try {
			 Authentication authentication = authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                		username,
		                        password
		                )
		        );

			 SecurityContextHolder.getContext().setAuthentication(authentication);
			return authService.signinUser(username, passwordEncoder.encode(password), authentication);
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				errorResponse = new ErrorResponse("BadRequest", "400", "Invalid username/password.");
				return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
			}
	}
    
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST )
	public ResponseEntity<?> registerUser( @RequestBody Map<String, Object> payload) {
		  System.out.println(payload);
		  String firstname = (String) payload.get("firstname");
		  String lastname = (String) payload.get("lastname");
		  String email = (String) payload.get("email");
		  String password = (String) payload.get("password");
		try {
			
			return authService.signupUser(firstname,lastname,email,passwordEncoder.encode(password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse = new ErrorResponse("BadRequest", "400", "Invalid e-mail address");
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}			
	}		

	
	@RequestMapping(value = "/verify", method = RequestMethod.POST )
	public ResponseEntity<?> verify( @RequestBody Map<String, Object> payload) {
		  System.out.println(payload);
		  String authcode = (String) payload.get("authcode");
		try {
			return authService.verify(authcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse = new ErrorResponse("BadRequest", "400", "Invalid token");
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}			
	}
}
