package com.kittydaddy.metadata.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.system.domain.PermissionEntity;

public interface PermissionEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(PermissionEntity record);

    int insertSelective(PermissionEntity record);

    PermissionEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PermissionEntity record);

    int updateByPrimaryKey(PermissionEntity record);
    
	List<PermissionEntity> queryPermissionByParentId(@Param(value="parentId") String id);

	List<PermissionEntity> queryPermissionByPage(@Param(value="moduleName") String name,@Param(value="tenantId") String tenantId,@Param(value="permissionType")String permissionType);

	List<PermissionEntity> queryCatalogPermission();

	List<PermissionEntity> queryPermissionByTenantId(@Param(value="tenantId") String tenantId);
	
	
}