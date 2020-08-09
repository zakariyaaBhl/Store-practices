package com.bhl.store.dao;

import com.bhl.store.entities.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
