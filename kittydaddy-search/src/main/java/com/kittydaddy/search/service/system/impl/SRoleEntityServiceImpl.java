package com.kittydaddy.search.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kittydaddy.search.model.system.SRoleEntity;
import com.kittydaddy.search.repository.system.SRoleEntityRepository;
import com.kittydaddy.search.service.system.SRoleEntityService;

@Service
public class SRoleEntityServiceImpl implements SRoleEntityService{

	private SRoleEntityRepository sRoleEntityRepository;
	
	@Autowired
	public void setsRoleEntityRepository(SRoleEntityRepository sRoleEntityRepository) {
		this.sRoleEntityRepository = sRoleEntityRepository;
	}
	
	@Override
	public List<SRoleEntity> findByRoleName(String roleName) {
		return sRoleEntityRepository.findByRoleName(roleName);
	}

	@Override
	public SRoleEntity save(SRoleEntity role) {
		return sRoleEntityRepository.save(role);
	}
}
