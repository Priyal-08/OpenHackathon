package com.openhack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebAppConfiguration
@ContextConfiguration(classes = Config.class)
public class UserRegistrationTest extends AbstractTest{
	
	   @Override
	   @Before
	   public void setUp() {
	      super.setUp();
	   }

	@Test
	public void testSigninUser() throws Exception {
		
//		String userRequestJson = "{\"username\":\"vihaan@sharklasers.com\",\"password\":\"SJSU\"}";
//
//	    String uri = "/auth/signin";
//	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//	         .contentType(MediaType.APPLICATION_JSON_VALUE)
//	         .content(userRequestJson)).andReturn();
//	      
//	      int status = mvcResult.getResponse().getStatus();
//	      assertEquals(200, status);
		
		MvcResult mockMvc = mvc.perform(MockMvcRequestBuilders.get("/")
		         .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
//		         .content(userRequestJson)).andReturn();
//		      (get("/"))
//        .perform(get("/"))
//        .andExpect(status().isOk());

	}
	
}