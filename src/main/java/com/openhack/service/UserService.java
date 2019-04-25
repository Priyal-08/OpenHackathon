package com.openhack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.dao.UserJPADao;

@Service
public class UserService {
	/** The user dao. */
	@Autowired
	private UserJPADao userDao;

	@Transactional
	public String signinUser(String username, String password) {

		return "200";
	}
}
