package com.openhack.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.domain.Organization;

@Repository
public class JpaOrganizationDao implements OrganizationDao{

	/** The entity manager. */
	@PersistenceContext
    private EntityManager entityManager;
	
	@Transactional
	@Override
	public List<Organization> findByIds(List<Long> ids) {
		TypedQuery<Organization> query = entityManager.createQuery("from organizations where id in :ids", Organization.class);
		query.setParameter("ids", ids);
		if (query.getResultList().isEmpty()) return null;
		return query.getResultList();
	}

}
