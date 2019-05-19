package com.openhack.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.domain.Payment;

@Repository
public class JpaDashboardDao implements DashboardDao{
	
	/** The entity manager. */
	@PersistenceContext
    private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Transactional
    @Override
	public List<Payment> findAllPayments() {
		Query query =
				 entityManager.createNativeQuery("SELECT * FROM payment", Payment.class);
		 return query.getResultList();
	}

}
