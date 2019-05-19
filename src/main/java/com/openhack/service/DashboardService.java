package com.openhack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.contract.ErrorResponse;
import com.openhack.contract.HackathonWiseRevenueResponse;
import com.openhack.dao.DashboardDao;
import com.openhack.domain.Payment;

@Service
public class DashboardService {

	@Autowired
	private DashboardDao dashboardDao;
	
	@Autowired ErrorResponse errorResponse;
	
	@Transactional
	public ResponseEntity<?> getHackathonWiseRevenue() {
		
		try {
			List<Payment> payments = dashboardDao.findAllPayments();
			
			List<HackathonWiseRevenueResponse> response = payments.stream().map(payment->new HackathonWiseRevenueResponse(
					payment.getHackathon().getId(), payment.getHackathon().getEventName(), payment.getAmount())).collect(Collectors.toList()); 
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}
		catch(Exception e) {
			errorResponse = new ErrorResponse("BadRequest", "400", e.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}
		
	}

}
