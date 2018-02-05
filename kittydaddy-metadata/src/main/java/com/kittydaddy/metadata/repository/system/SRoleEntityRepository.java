package com.kittydaddy.metadata.repository.system;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.kittydaddy.metadata.system.domain.search.SRoleEntity;

public interface SRoleEntityRepository extends ElasticsearchRepository<SRoleEntity, String>{
	  //根据名称查询
	  List<SRoleEntity> findByRoleName(String roleName);
}
