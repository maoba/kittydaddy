package com.kittydaddy.metadata.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.system.domain.RoleEntity;

public interface RoleEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(RoleEntity record);

    int insertSelective(RoleEntity record);

    RoleEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleEntity record);

    int updateByPrimaryKey(RoleEntity record);
    
	List<RoleEntity> queryRolesByName(@Param(value="name") String name,@Param(value="tenantId") String tenantId);

	List<RoleEntity> queryRolesByTeanantId(@Param(value="tenantId") String tenantId);

	RoleEntity queryRoleByCodeAndTenantId(@Param(value="roleCode") String roleCode,@Param(value="tenantId")Long tenantId);
}