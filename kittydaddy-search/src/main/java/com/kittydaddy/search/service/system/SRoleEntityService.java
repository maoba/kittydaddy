package com.kittydaddy.search.service.system;

import java.util.List;

import com.kittydaddy.search.model.system.SRoleEntity;

public interface SRoleEntityService {
	List<SRoleEntity> findByRoleName(String roleName);

	SRoleEntity save(SRoleEntity role);
}
