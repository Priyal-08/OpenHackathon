package com.openhack.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.openhack.contract.AuthResponse;
import com.openhack.contract.ErrorResponse;
import com.openhack.security.JwtTokenProvider;
import com.openhack.service.AuthService;

@RunWith(SpringRunner.class)

@WebMvcTest(value = AuthController.class, secure = false)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private JwtTokenProvider tokenProvider;
	
	@MockBean
	private ErrorResponse errorResponse;
	
	String signinRequest = "{\"username\":\"vihaan@sharklasers.com\",\"password\":\"SJSU\"}";

	private ResponseEntity r;

	@Test
	public void authenticateUser() throws Exception {

		AuthResponse authResponse = new AuthResponse(15, "Hacker", "vihaan", "vihaan", "Bearer token");
		r = new ResponseEntity<AuthResponse>(authResponse, 
				HttpStatus.OK);
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		authenticationManager = Mockito.mock(AuthenticationManager.class);
		
		Mockito.when(authenticationManager.authenticate(Mockito.<Authentication> any())).thenReturn(a);
		Mockito.when(
				authService.signinUser(Mockito.anyString(),
						Mockito.anyString(),Mockito.any(Authentication.class))).thenReturn(r);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/auth/signin").accept(
				MediaType.APPLICATION_JSON).content(signinRequest)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

}