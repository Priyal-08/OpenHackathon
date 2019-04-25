package com.openhack.dao;

import com.openhack.domain.UserProfile;

/**
 * The Interface EmployeeDao.
 */
public interface UserDao{
	
	/**
	 * Store.
	 *
	 * @param employee the employee
	 * @return the employee
	 * @throws Exception the exception
	 */
	public UserProfile store(UserProfile user) throws Exception;
	
	/**
	 * Find by username.
	 *
	 * @param employeeId the employee id
	 * @return the employee
	 */
	public UserProfile findByUsername(String username);
	
}