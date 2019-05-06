package com.openhack.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.domain.Hackathon;
import com.openhack.domain.Organization;
import com.openhack.domain.UserProfile;

@Repository
public class JpaOrganizationDao implements OrganizationDao{

	/** The entity manager. */
	@PersistenceContext
    private EntityManager entityManager;

	@Transactional
	@Override
	public List<Organization> findByIds(List<Long> ids) {
		Query query = entityManager.createNativeQuery("SELECT * FROM ORGANIZATION O WHERE O.ID IN :ids", Organization.class);
		query.setParameter("ids", ids);
		if (query.getResultList().isEmpty()) return null;
		return query.getResultList();
	}

	@Transactional
	@Override
	public Organization store(Organization organization) {
		return entityManager.merge(organization);
	}

	@Transactional
	@Override
	public Organization findByOrganizationName(String name) {
		
		Query query = entityManager.createNativeQuery("SELECT * FROM organization o WHERE o.name LIKE :name", Organization.class);
		query.setParameter("name", name);
		if (query.getResultList().isEmpty()) return null;
		return (Organization)query.getResultList().get(0);
	}

	@Override
	public Organization findById(long id) {
		return entityManager.find(Organization.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Organization> listOrganizations() {
		Query query = entityManager.createNativeQuery("SELECT * FROM organization", Organization.class);
		 return query.getResultList();
	}
//	@Transactional
//	@Override
//	public Organization store(Organization organization) {
//		return entityManager.merge(organization);
//	}
//
//	@Override
//	public Organization findById(long id) {
//		return entityManager.find(Organization.class, id);
//	}
//	
//	@Transactional
//	@Override
//	public Organization findByName(String name) {
//		return entityManager.find(Organization.class, name);
//	}
//
//	@Override
//	public List<Organization> listOrganizations() {
//		
//		Query query = entityManager.createNativeQuery("SELECT * FROM organizations", Organization.class);
//		 return query.getResultList();
//	}
}
