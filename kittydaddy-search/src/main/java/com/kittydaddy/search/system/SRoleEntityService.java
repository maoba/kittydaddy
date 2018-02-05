package com.kittydaddy.search.system;

import java.util.List;

import com.kittydaddy.metadata.system.domain.search.SRoleEntity;

public interface SRoleEntityService {
	List<SRoleEntity> findByRoleName(String roleName);

	SRoleEntity save(SRoleEntity role);
}
