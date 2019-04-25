package com.openhack.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.service.UserService;

@RestController
@Transactional
@RequestMapping("/")
public class UserController {
	/** The employee service. */
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/signin", method = RequestMethod.POST )
	public String signin(
			@RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {
		
		return userService.signinUser(username, password);
	}
	
}
