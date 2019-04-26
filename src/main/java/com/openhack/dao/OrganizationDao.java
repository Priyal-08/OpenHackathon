package com.openhack.dao;

import java.util.List;

import com.openhack.domain.Organization;

public interface OrganizationDao {

	List<Organization> findByIds(List<Long> ids);
}
