package com.openhack.dao;


import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.openhack.domain.UserProfile;

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
    	
    	UserProfile u = new UserProfile();
		u = entityManager.merge(user);
    	return u;
	}
	
	@Override
	public UserProfile findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<UserProfile> findByIds(List<Long> ids){
		TypedQuery<UserProfile> query = entityManager.createQuery("from userprofile where id in :ids", UserProfile.class);
		query.setParameter("ids", ids);
		if (query.getResultList().isEmpty()) return null;
		return query.getResultList();
	}
}
