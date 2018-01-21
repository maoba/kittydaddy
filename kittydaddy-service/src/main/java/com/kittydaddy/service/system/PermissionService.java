package com.kittydaddy.service.system;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.facade.dto.system.LeftMenusDto;
import com.kittydaddy.facade.dto.system.PermissionDto;
import com.kittydaddy.metadata.system.domain.PermissionEntity;

public interface PermissionService {
	
	/**
	 * 查询所有的父级目录
	 * @return
	 */
	List<PermissionDto> queryCatalogPermissions();
    
    
	/**
	 * 查询左侧菜单
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	List<LeftMenusDto> queryLeftMenus(String userId, String tenantId);
    
	/**
	 * 根据租户的id查询树
	 * @param tenantId
	 * @return
	 */
	List<Map<String, Object>> queryPermissionTreeList(String tenantId);

	/**
	 * 根据权限名称，租户Id，分页相关信息进行查询
	 * @param name
	 * @param tenantId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<PermissionEntity> queryPermissionsByPage(String name,String permissionType, String tenantId, Integer pageIndex,Integer pageSize);

    /**
     * 保存更新
     * @param params
     */
	void saveUpdatePermission(Map<String, Object> params);

    /**
     * 根据权限id进行删除
     * @param permissionId
     */
	void deleteRelativeEntityById(String permissionId);

    /**
     * 根据id查询权限
     * @param permissionId
     * @return
     */
	PermissionEntity queryPermissionById(String permissionId);


	List<Map<String, Object>> permissionTreeCheckedList(String roleId);

}
