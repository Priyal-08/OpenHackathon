package com.openhack.dao;

import java.util.List;

import com.openhack.domain.Payment;

/**
 * The Interface DashboardDao.
 */
public interface DashboardDao {

	public List<Payment> findAllPayments();
	
}
