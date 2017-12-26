package com.kittydaddy.service.system.impl;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kittydaddy.common.utils.IdSplitUtil;
import com.kittydaddy.facade.convert.system.RolePermissionConvert;
import com.kittydaddy.facade.dto.system.RolePermissionDto;
import com.kittydaddy.facade.dto.system.request.RolePermissionRequest;
import com.kittydaddy.metadata.system.dao.PermissionEntityMapper;
import com.kittydaddy.metadata.system.dao.RoleEntityMapper;
import com.kittydaddy.metadata.system.dao.RolePermissionEntityMapper;
import com.kittydaddy.metadata.system.domain.PermissionEntity;
import com.kittydaddy.metadata.system.domain.RoleEntity;
import com.kittydaddy.metadata.system.domain.RolePermissionEntity;
import com.kittydaddy.metadata.system.domain.UserRoleEntity;
import com.kittydaddy.service.system.RolePermissionService;
/**
 * @author kitty daddy
 *  角色权限服务
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService{

	@Autowired
	private RolePermissionEntityMapper rolePermissionMapper;
	
	@Autowired
	private PermissionEntityMapper permissionMapper;
	
	@Autowired
	private RoleEntityMapper roleMapper;
	
	@Override
	public Set<String> queryRolePermission(List<UserRoleEntity> userRoles) {
		Set<String> permissionCodes = null;
		if(CollectionUtils.isNotEmpty(userRoles)){
			permissionCodes = new HashSet<String>();
			
			for(UserRoleEntity userRoleEntity : userRoles){
				//根据角色id查看角色权限
				List<RolePermissionEntity> rolePermissions = rolePermissionMapper.queryRolePermissionByRoleId(userRoleEntity.getRoleId());
				
				//组装编码
				if(CollectionUtils.isNotEmpty(rolePermissions)){
					for(RolePermissionEntity rolerPermissionEntity : rolePermissions){
						permissionCodes.add(rolerPermissionEntity.getPermissionCode());
					}
				}
			}
		}
		return permissionCodes;
	}

	@Override
	public Set<RolePermissionEntity> queryRolePermissionEntity(List<UserRoleEntity> userRoles) {
		    if(CollectionUtils.isEmpty(userRoles)) return null;
		    Set<RolePermissionEntity> rolePermissionEntities = new HashSet<RolePermissionEntity>();
			
			for(UserRoleEntity userRoleEntity : userRoles){
				//根据角色id查看角色权限
				List<RolePermissionEntity> rolePermissions = rolePermissionMapper.queryRolePermissionByRoleId(userRoleEntity.getRoleId());
				
				//重新组装实体类
				if(CollectionUtils.isNotEmpty(rolePermissions)){
					for(RolePermissionEntity rolerPermissionEntity : rolePermissions){
						rolePermissionEntities.add(rolerPermissionEntity);
					}
				}
			}
		return rolePermissionEntities;
	}



	@Override
	public void deleteByRoleId(String roleId) {
		rolePermissionMapper.deleteByRoleId(roleId);
	}

//	@Override
//	public List<RolePermissionDto> queryRolePermissionByRoleId(Long roleId) {
//		List<RolePermissionEntity> entities = rolePermissionMapper.queryRolePermissionByRoleId(roleId);
//		List<RolePermissionDto> rolePermissionDtos = RolePermissionConvert.convertEntity2Dto(entities);
//		return rolePermissionDtos;
//	}

//	@Override
//	public void saveRolePermission(RolePermissionRequest request) {
//		//根据角色id删除角色权限关系
//        rolePermissionMapper.deleteByRoleId(request.getRoleId());
//        
//        //重新插入角色权限关系
//        Set<Long> permissionIds = IdSplitUtil.splitString2Long(request.getPermissionIds());
//        if(CollectionUtils.isNotEmpty(permissionIds)){
//        	for(Long permissionId : permissionIds){
//        		 RolePermissionEntity entity = new RolePermissionEntity();
//        		 entity.setCreateTime(new Date());
//        		 PermissionEntity permissionEntity = permissionMapper.selectByPrimaryKey(permissionId);
//        		 if(permissionEntity!=null){
//        			 entity.setPermissionCode(permissionEntity.getPermissionCode());
//        		 }
//        		 entity.setPermissionId(permissionId);
//        		 entity.setRoleId(request.getRoleId());
//        		 RoleEntity roleEntity = roleMapper.selectByPrimaryKey(request.getRoleId());
//        		 if(roleEntity!=null){
//        			 entity.setRoleName(roleEntity.getRoleName());
//        		 }
//        		 rolePermissionMapper.insert(entity);
//        	}
//        }
//	}

}
