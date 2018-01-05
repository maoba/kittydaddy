package com.kittydaddy.service.system;
public interface UserRoleService {
	/**
	 * 根绝角色id解除角色用户的权限
	 * @param roleId
	 */
	void deleteByRoleId(String roleId);
     
	/**
	 * 根据用户id解除用户角色的关系
	 * @param userId
	 */
	void deleteByUserId(String userId);
    
}
