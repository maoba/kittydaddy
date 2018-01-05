package com.kittydaddy.service.system.impl;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kittydaddy.common.utils.KStringUtils;
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

	@Override
	@Transactional
	public void saveRolePermission(Map<String, Object> params) {
		if(null != params.get("pmIds") && null!= params.get("roleId")){
		     List<String> permissionIds = KStringUtils.splitString(params.get("pmIds").toString());
		     String roleId = (String) params.get("roleId");
		     RoleEntity roleEntity = roleMapper.selectByPrimaryKey(roleId);
		     //根据roleId清除原本的角色权限之间的关系
		     rolePermissionMapper.deleteByRoleId(roleId);
		     //建立新的角色权限之间的关系
		     for(String permissionId : permissionIds){
		    	 RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
		    	 rolePermissionEntity.setCreateTime(new Date());
		    	 PermissionEntity entity = permissionMapper.selectByPrimaryKey(permissionId);
		    	 rolePermissionEntity.setPermissionCode(entity.getPermissionCode());
		    	 rolePermissionEntity.setPermissionId(permissionId);
		    	 rolePermissionEntity.setRoleId(roleId);
		    	 rolePermissionEntity.setRoleName(roleEntity.getRoleName());
		    	 rolePermissionMapper.insert(rolePermissionEntity);
		     }
		}
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
