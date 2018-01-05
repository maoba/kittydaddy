package com.kittydaddy.service.system;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.metadata.system.domain.RoleEntity;

public interface RoleService {
     
	/**
	 * 分页查询
	 * @param name 角色名称
	 * @param tenantId  租户id
	 * @param pageIndex  当前页码
	 * @param pageSize  一页上面的总记录数
	 * @return
	 */
	PageInfo<RoleEntity> queryRolesByPage(String name, String tenantId, Integer pageIndex, Integer pageSize);

	/**
	 * 
	 * @param object
	 * @param tenantId
	 * @param object2
	 * @param object3
	 * @return
	 */
	PageInfo<RoleEntity> queryRolesByPage(Map<String,Object> params , String tenantId);
    
	/**
	 * 根据ID查询角色实体类
	 * @param id
	 * @return
	 */
	RoleEntity queryRolesById(String id);
    
	/**
	 * 保存或者更新角色
	 * @param params
	 */
	void saveUpdateRole(Map<String, Object> params);

	
	/**
	 * 根据角色ID删除角色
	 * @param roleId
	 */
	void deleteById(String roleId);

}
