package com.openhack.dao;

import java.util.List;

import com.openhack.domain.Organization;

public interface OrganizationDao {

	List<Organization> findByIds(List<Long> ids);
	 /*
		 * Create organization entity.
		 *
		 * @param organization: the organization
		 * @return the organization
		 */
		public Organization store(Organization organization);
		
		/*
		 * Get Organization entity by Organization id.
		 *
		 * @param Organization: the Organization
		 * 
		 * @return the Organization
		 */
		public Organization findById(long id);
		
		/*
		 * Get Organization entity by organization name.
		 *
		 * @param Organization: the Organization
		 * 
		 * @return Organization
		 */
		public Organization findByOrganizationName(String name);
		
		/*
		 * List Organization entity.
		 * 
		 * @return list of organizations
		 */
		public List<Organization> listOrganizations();
}
