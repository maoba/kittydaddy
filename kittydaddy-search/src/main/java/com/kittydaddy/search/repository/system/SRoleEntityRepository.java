package com.kittydaddy.search.repository.system;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.kittydaddy.search.model.system.SRoleEntity;

public interface SRoleEntityRepository extends ElasticsearchRepository<SRoleEntity, String>{
	  //根据名称查询
	  List<SRoleEntity> findByRoleName(String roleName);
}
