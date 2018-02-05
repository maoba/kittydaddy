package com.kittydaddy.service.search.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kittydaddy.metadata.repository.system.SRoleEntityRepository;
import com.kittydaddy.metadata.system.domain.search.SRoleEntity;
import com.kittydaddy.service.search.SRoleEntityService;

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
