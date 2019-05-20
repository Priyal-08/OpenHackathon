package com.openhack.dao;

import java.util.List;

import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
import com.openhack.domain.UserRole;

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
	
	public UserAccount store(UserAccount user) throws Exception;
	
	public UserRole store(UserRole user) throws Exception;

	/**
	 * Find by username.
	 *
	 * @param employeeId the employee id
	 * @return the employee
	 */
	public UserProfile findByUsername(String username);
	
	/*
	 * Get users by user ids.
	 *
	 * @param ids: list of user ids
	 * @return list of users
	 */
	List<UserProfile> findByIds(List<Long> ids);
	
	/*
	 * Get user by user id.
	 *
	 * @param id: the user id
	 * @return the user
	 */
	public UserProfile findById(long id);
	
	public UserRole findRoleById(long id);
	
	public UserProfile findByEmail(String email);
	
	public UserAccount findByAuthCode(String authcode);
	
	public UserAccount findByUserAndPassword(String username, String password);

	public UserProfile findByScreenname(String screenname);
	
	public List<UserProfile> listHackers();
	
	public UserAccount findAccountByuserId(long id);
	
	public List<UserRole> findAllRolesById(long id);
	
	public UserAccount findAccountByEmail(String username);

	public List<UserProfile> findPendingRequests(long orgId);
	
}