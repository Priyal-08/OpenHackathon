package com.openhack.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.service.DashboardService;

/**
 * The Class DashboardController.
 */
@CrossOrigin
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	
	@RequestMapping(value="/hackathonRevenue", method = RequestMethod.GET)
	
	public ResponseEntity<?> getHackathonWiseRevenue(@RequestParam long userId) {
		
		return dashboardService.getHackathonWiseRevenue();
	}
}
