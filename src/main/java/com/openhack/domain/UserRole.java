package com.openhack.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "USERROLE")
public class UserRole {
	
	@EmbeddedId 
	private UserRoleId id;
	
	/** The role. */
	@Column(name = "ROLE")
	private String role;

	public UserRole(UserRoleId id, String role) {
		this.id = id;
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
