package com.kittydaddy.service.system;
import java.util.List;
import java.util.Set;
import com.kittydaddy.metadata.system.domain.RolePermissionEntity;
import com.kittydaddy.metadata.system.domain.UserRoleEntity;
public interface RolePermissionService {
    
	/**
	 * 根据用户角色关系查询角色编码
	 * @param userRoles
	 * @return
	 */
	Set<String> queryRolePermission(List<UserRoleEntity> userRoles);
    
	/**
	 * 根据用户角色关系查询角色权限实体
	 * @param userRoles
	 * @return
	 */
	Set<RolePermissionEntity> queryRolePermissionEntity(List<UserRoleEntity> userRoles);

    
	/**
	 * 根绝角色Id解除角色权限的关系
	 * @param roleId
	 */
	void deleteByRoleId(String roleId);
    
//	/**
//	 * 查询某个角色已经绑定过的角色
//	 * @param roleId
//	 * @return
//	 */
//	List<RolePermissionDto> queryRolePermissionByRoleId(Long roleId);
    
//	/**
//	 * 保存角色权限
//	 * @param request
//	 */
//	void saveRolePermission(RolePermissionRequest request);
}
