package com.kittydaddy.service.system;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.facade.dto.system.RoleDto;
import com.kittydaddy.facade.dto.system.request.RoleRequest;
import com.kittydaddy.metadata.system.domain.RoleEntity;

public interface RoleService {
     
	/**
	 * 保存角色
	 * @param request
	 */
	void saveRole(RoleRequest request);
    
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
	 * 更新角色
	 * @param request
	 */
	void update(RoleRequest request);
    
	/**
	 * 根据租户的id查询租户的信息
	 * @param tenantId [租户id]
	 * @return
	 */
	List<RoleDto> queryRolesByTenantId(Long tenantId);
    
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
