package com.openhack.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
import com.openhack.domain.UserRole;

/**
 * The Class JpaEmployeeDao.
 */
@Repository
public class UserJPADao implements UserDao {

	/** The entity manager. */
	@PersistenceContext
    private EntityManager entityManager;
	
    /* (non-Javadoc)
     * @see com.lab2.dao.EmployeeDao#store(com.lab2.domain.Employee)
     */
    @Override
	public UserProfile store(UserProfile user) throws Exception{
		return entityManager.merge(user);
	}
    
    /* (non-Javadoc)
     * @see com.lab2.dao.EmployeeDao#store(com.lab2.domain.Employee)
     */
    @Override
	public UserAccount store(UserAccount user) throws Exception{
		return entityManager.merge(user);
	}
    
    @Override
	public UserRole store(UserRole user) throws Exception{
		return entityManager.merge(user);
	}
	
	@Override
	public UserProfile findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	@Override
	@Transactional
	public UserProfile findByEmail(String email) {
			Query query = entityManager.createNativeQuery("SELECT * FROM userprofile u WHERE u.email LIKE :email", UserProfile.class);
			query.setParameter("email", email);
			if (query.getResultList().isEmpty()) return null;
			return (UserProfile)query.getResultList().get(0);
	}
	
	@Override
	@Transactional
	public UserProfile findByScreenname(String screenname) {
			Query query = entityManager.createNativeQuery("SELECT * FROM userprofile u WHERE u.screenname LIKE :screenname", UserProfile.class);
			query.setParameter("screenname", screenname);
			if (query.getResultList().isEmpty()) return null;
			return (UserProfile)query.getResultList().get(0);
	}
	
	@Transactional
	public UserAccount findByAuthCode(String authcode) {
			Query query = entityManager.createNativeQuery("SELECT * FROM useraccount u WHERE u.authcode LIKE :authcode", UserAccount.class);
			query.setParameter("authcode", authcode);
			if (query.getResultList().isEmpty()) return null;
			return (UserAccount)query.getResultList().get(0);
	}
	
	@Transactional
	public UserAccount findByUserAndPassword(String username, String password) {
			Query query = entityManager.createNativeQuery("SELECT ua.* FROM useraccount ua, userprofile up WHERE ua.userid = up.id and ua.status = 'Active' and up.email LIKE :username and ua.password LIKE :password", UserAccount.class);
			query.setParameter("username", username);
			query.setParameter("password", password);			
			if (query.getResultList().isEmpty()) return null;
			return (UserAccount)query.getResultList().get(0);
	}
	
	/* (non-Javadoc)
     * @see com.lab2.dao.UserDao#findByIds(List<Long> ids)
     */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserProfile> findByIds(List<Long> ids){
		Query query = entityManager.createNativeQuery("select * from userprofile where id in :ids", UserProfile.class);
		query.setParameter("ids", ids);
		if (query.getResultList().isEmpty()) return null;
		return query.getResultList();
	}

	/* (non-Javadoc)
     * @see com.lab2.dao.UserDao#findById(long)
     */
	@Override
	@Transactional
	public UserProfile findById(long id) {
		return entityManager.find(UserProfile.class, id);
	}
	
	/* (non-Javadoc)
     * @see com.lab2.dao.UserDao#findById(long)
     */
	@Override
	@Transactional
	public UserRole findRoleById(long id) {
		Query query = entityManager.createNativeQuery("select * from userrole where userid = :id", UserRole.class);
		query.setParameter("id", id);
		if (query.getResultList().isEmpty()) return null;
		return (UserRole)query.getResultList().get(0);
	}
	
	@Override
	@Transactional
	public UserAccount findAccountByuserId(long id) {
		Query query = entityManager.createNativeQuery("select * from useraccount where userid = :id", UserAccount.class);
		query.setParameter("id", id);
		if (query.getResultList().isEmpty()) return null;
		return (UserAccount)query.getResultList().get(0);
	}
	
	@Override
	@Transactional
	public UserAccount findAccountByEmail(String username) {
		Query query = entityManager.createNativeQuery("SELECT ua.* FROM useraccount ua, userprofile up WHERE ua.userid = up.id and ua.status = 'Active' and up.email LIKE :username", UserAccount.class);
		query.setParameter("username", username);
		if (query.getResultList().isEmpty()) return null;
		return (UserAccount)query.getResultList().get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserRole> findAllRolesById(long id) {
		Query query = entityManager.createNativeQuery("select * from userrole where userid = :id", UserRole.class);
		query.setParameter("id", id);
		if (query.getResultList().isEmpty()) return null;
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<UserProfile> listHackers() {
		Query query = entityManager.createNativeQuery("SELECT up.* FROM userprofile up, userrole ur where up.id = ur.userid and ur.role = 'Hacker'", UserProfile.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<UserProfile> findPendingRequests(long orgId) {
		
		Query query = entityManager.createNativeQuery("SELECT up.* FROM userprofile up where up.membership_status = 'requested' ", UserProfile.class);
		return query.getResultList();
		
	}

}
