package com.kittydaddy.service.system.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.internal.jobs.ObjectMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.facade.convert.system.PermissionConvert;
import com.kittydaddy.facade.dto.system.PermissionDto;
import com.kittydaddy.facade.dto.system.PermissionTreeDto;
import com.kittydaddy.facade.dto.system.request.PermissionRequest;
import com.kittydaddy.facade.dto.system.response.PermissionTreeResponse;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantPermissionRequest;
import com.kittydaddy.metadata.system.dao.PermissionEntityMapper;
import com.kittydaddy.metadata.system.dao.RoleEntityMapper;
import com.kittydaddy.metadata.system.dao.RolePermissionEntityMapper;
import com.kittydaddy.metadata.system.dao.UserRoleEntityMapper;
import com.kittydaddy.metadata.system.domain.PermissionEntity;
import com.kittydaddy.metadata.system.domain.RoleEntity;
import com.kittydaddy.metadata.system.domain.RolePermissionEntity;
import com.kittydaddy.metadata.system.domain.UserRoleEntity;
import com.kittydaddy.metadata.util.RedisUtil;
import com.kittydaddy.service.system.PermissionService;
import com.kittydaddy.service.system.RolePermissionService;
import com.kittydaddy.service.system.UserRoleService;
/**
 * @author kitty daddy
 * 权限服务
 */
@Service
public class PermissionServiceImpl implements PermissionService{

	@Autowired
    private PermissionEntityMapper permissionMapper;
	
	@Autowired
	private RoleEntityMapper roleEntityMapper;
	
	@Autowired
	private UserRoleEntityMapper userRoleMapper;
	
	@Autowired
	private RolePermissionEntityMapper rolePermissionMapper;
	
	/**
	 * 用户角色服务
	 */
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 角色权限服务
	 */
	@Autowired
	private RolePermissionService rolePermissionService;
	
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void savePermission(PermissionRequest request) {
		if(request.getParentId()!=null){
			PermissionEntity entity = permissionMapper.selectByPrimaryKey(request.getParentId());
            if(entity!=null){
            	request.setParentName(entity.getModuleName());
            }
		}
		PermissionEntity entity = PermissionConvert.convertRequest2Entity(request);	
		if(entity!=null){
			entity.setStatus(StatusEnum.NORMAL.getValue());
			entity.setCreateTime(new Date());
			permissionMapper.insert(entity);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override//分业查询权限dto
	public PageInfo<PermissionDto> queryPermissionByPage(String name,Long tenantId, Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize, true, null, true);
		List<PermissionEntity> entitys = permissionMapper.queryPermissionByPage(name,tenantId);
		PageInfo pageInfo = new PageInfo(entitys);
		List<PermissionDto> permissionDtos = PermissionConvert.convertEntitys2Dtos(entitys);
		pageInfo.setList(permissionDtos);
		return pageInfo;
	}

	@Override
	public List<PermissionTreeResponse> queryPermissionTree(Long userId, Long tenantId) {
		//TODO   此处代码可以进行优化处理（可以直接存入redis缓存中，之后作改造）
		//获取用户角色实体类
		List<UserRoleEntity> userRoleEntitys = userRoleService.queryUserRole(userId, tenantId);

		//查询获取角色权限实体类
		Set<RolePermissionEntity> rolePermissionEntitys = rolePermissionService.queryRolePermissionEntity(userRoleEntitys);

		//查询权限组装权限
        List<PermissionTreeResponse> response = buildPermissionTree(rolePermissionEntitys);
		List<Map<String,Object>> resources = new ArrayList<Map<String,Object>>();


		return response;
	}


	@Override
	public List<PermissionTreeResponse> queryMenus(Long userId, Long tenantId) {
		//TODO   此处代码可以进行优化处理（可以直接存入redis缓存中，之后作改造）
		try{
            //获取用户角色实体类
			List<UserRoleEntity> userRoleEntitys = userRoleService.queryUserRole(userId, tenantId);
			//查询获取角色权限实体类
			Set<RolePermissionEntity> rolePermissionEntitys = rolePermissionService.queryRolePermissionEntity(userRoleEntitys);
			//查询权限组装权限
			List<PermissionTreeResponse> menus = buildPermissionTree(rolePermissionEntitys);
			return menus;
		}catch (NullPointerException e){
			e.printStackTrace();
		}
          return null;
	}
    
	/**
	 * 组装权限树
	 * @param rolePermissionEntitys
	 * @return
	 */
	private List<PermissionTreeResponse> buildPermissionTree(Set<RolePermissionEntity> rolePermissionEntitys) {
		//获取最上层的权限
		Set<PermissionEntity> topPermissions = null;
		
		List<PermissionTreeResponse> responses = null;
		Set<Long> permissionIdContainer = new HashSet<Long>();
		if(CollectionUtils.isNotEmpty(rolePermissionEntitys)){
		     topPermissions = new HashSet<PermissionEntity>();
		     
		     for(RolePermissionEntity rolePermissionEntity : rolePermissionEntitys){
		    	   PermissionEntity permissionEntity = permissionMapper.selectByPrimaryKey(rolePermissionEntity.getPermissionId());
		    	    //获取最上级的树
		    	   if(permissionEntity.getParentId() == 0){
		    		    topPermissions.add(permissionEntity);
		    	   }
		    	   
		    	   permissionIdContainer.add(rolePermissionEntity.getPermissionId());
		     } 	
		}
		
		if(CollectionUtils.isNotEmpty(topPermissions)){
			responses = new ArrayList<PermissionTreeResponse>();
			//最上层的树 最终进行组装
			for(PermissionEntity topEntity : topPermissions){
				  List<PermissionEntity> childEntitys = permissionMapper.queryPermissionByParentId(topEntity.getId());
				  //过滤掉权限中不存在的权限实体
				  List<PermissionEntity> filterEntitys = this.filterPermissionEntity(childEntitys,permissionIdContainer);
				  
				  PermissionTreeResponse treeResponse = PermissionConvert.convertEntity2TreeResponse(topEntity,filterEntitys);
				  responses.add(treeResponse);
			}
		}
		return responses;
	}
    
	/**
	 * 过滤掉不在权限中的菜单
	 * @param childEntitys
	 * @param permissionIdContainer
	 * @return
	 */
	private List<PermissionEntity> filterPermissionEntity(List<PermissionEntity> childEntitys, Set<Long> permissionIdContainer) {
        List<PermissionEntity> finalPermissionEntities = null;
		if(CollectionUtils.isNotEmpty(childEntitys)){
			finalPermissionEntities = new ArrayList<PermissionEntity>();
        	for(PermissionEntity permissionEntity : childEntitys){
        		if(permissionIdContainer.contains(permissionEntity.getId())){
        			finalPermissionEntities.add(permissionEntity);
        		}
        	}
        }
		return finalPermissionEntities;
	}

	@Override
	public List<PermissionDto> queryCatalogPermissions() {
		List<PermissionEntity> entitys = permissionMapper.queryCatalogPermission();
		List<PermissionDto> permissionDtos = PermissionConvert.convertEntitys2Dtos(entitys);
		return permissionDtos;
	}

	@Override
	public void delete(Set<Long> ids) {
         if(CollectionUtils.isNotEmpty(ids)){
        	 for(Long id : ids){
        		 permissionMapper.deleteByPrimaryKey(id);
        	 }
         }		
	}

	@Override
	public void updatePermission(PermissionRequest request) {
		PermissionEntity oldParentEntity = permissionMapper.selectByPrimaryKey(request.getParentId());
        PermissionEntity oldEntity = permissionMapper.selectByPrimaryKey(request.getId());   
		oldEntity = PermissionConvert.convertRequest2Entity(oldEntity,request);
		if(oldParentEntity!=null){
			oldEntity.setParentName(oldParentEntity.getModuleName());
		}
		oldEntity.setUpdateTime(new Date());
        permissionMapper.updateByPrimaryKey(oldEntity);
	}

	@Override
	public List<PermissionTreeDto> queryPermissionByTenantId(Long tenantId) {
		List<PermissionEntity> entities = permissionMapper.queryPermissionByTenantId(tenantId);
		List<PermissionTreeDto> treeDtos = PermissionConvert.convertEntity2TreeDto(entities);
		return treeDtos;
	}

	@Override
	public List<PermissionDto> queryPermissionDtoByTenantId(Long tenantId) {
		List<PermissionEntity> entities = permissionMapper.queryPermissionByTenantId(tenantId);
		List<PermissionDto> permissionDtos = PermissionConvert.convertEntitys2Dtos(entities);
		return permissionDtos;
	}

	@Override
	public void openPermission(TenantPermissionRequest request) {
          List<Long> permissionIds = request.getPermissionIds();	
          //清空原权限，新增新权限
          List<PermissionEntity> permissionEntities = permissionMapper.queryPermissionByTenantId(request.getTenantId());//存在的权限
		     if(CollectionUtils.isNotEmpty(permissionEntities)){
		    	  for(PermissionEntity existPermission : permissionEntities){
		    		    permissionMapper.deleteByPrimaryKey(existPermission.getId());
		    	   }
		     }
		  Set<Long> newPermissionIds = new HashSet<Long>();   
		  //重新分配权限   
          if(CollectionUtils.isNotEmpty(permissionIds)){
        	  /**
        	   * 入库父集目录
        	   */
        	  Map<String,PermissionEntity> parentEntityMap = this.getParentId(permissionIds,request.getTenantId());
        	  //入子目录
        	  for(Long permissionId : permissionIds){
        		  PermissionEntity entity = permissionMapper.selectByPrimaryKey(permissionId);
        		  //判断父权限编码是否一致
        		  if(entity !=null && StringUtils.isNotEmpty(entity.getPermissionUrl())){
        			  String originCode = permissionMapper.selectByPrimaryKey(entity.getParentId()).getPermissionCode();
        			  PermissionEntity parentEntity = parentEntityMap.get(originCode);
        			  if(parentEntity == null) return;
        			  entity.setTenantId(request.getTenantId());
        			  entity.setParentId(parentEntity.getId());
            		  entity.setId(null);
            		  permissionMapper.insert(entity);
            		  newPermissionIds.add(parentEntity.getId());
            		  newPermissionIds.add(entity.getId());
        		  }
        	  }
          }
          
          //1、自动生成角色信息，绑定分配权限
          //判断root权限的角色是否存在
          RoleEntity oldRoleEntity = roleEntityMapper.queryRoleByCodeAndTenantId("root",request.getTenantId());
          if(oldRoleEntity == null){
        	  RoleEntity roleEntity = new RoleEntity();
        	  roleEntity.setCreateTime(new Date());
        	  roleEntity.setDescription("super manager");
        	  roleEntity.setRoleCode("root");
        	  roleEntity.setRoleName("超级管理员");
        	  roleEntity.setTenantId(request.getTenantId());
        	  roleEntityMapper.insert(roleEntity);
        	  oldRoleEntity = roleEntity;
          }
          
          //更新角色权限
          this.updateRolePermission(oldRoleEntity, newPermissionIds);
    	  
    	  //2、给用户绑定角色
          List<UserRoleEntity> userRoleEntities = userRoleMapper.queryRole(request.getUserId(), request.getTenantId());
          if(CollectionUtils.isEmpty(userRoleEntities)){
        	  UserRoleEntity userRoleEntity = new UserRoleEntity();
        	  userRoleEntity.setRoleCode("root");
        	  userRoleEntity.setRoleId(oldRoleEntity.getId());
        	  userRoleEntity.setRoleName(oldRoleEntity.getRoleName());
        	  userRoleEntity.setTenantId(request.getTenantId());
        	  userRoleEntity.setUserId(request.getUserId());
        	  userRoleEntity.setUserName("超级管理员");
        	  userRoleMapper.insert(userRoleEntity);
          }
	}

	/**
	   * 获取父集目录id
	   */
	private Map<String,PermissionEntity> getParentId(List<Long> permissionIds,Long tenantId) {
		Map<String,PermissionEntity> map = null;
	    if(CollectionUtils.isNotEmpty(permissionIds)){
	    	map = new HashMap<String, PermissionEntity>();
	    	for(Long permissionId : permissionIds){
	    		 PermissionEntity entity = permissionMapper.selectByPrimaryKey(permissionId);
	    		 if(entity!=null & entity.getParentId() == 0){
	    			  entity.setTenantId(tenantId);
	        		  entity.setId(null);
	        		  permissionMapper.insert(entity);
	        		  map.put(entity.getPermissionCode(), entity);
	    		 }
	    	}
	    }
		return map;
	}

	/**
	 * 更新角色权限关系
	 */
	private void updateRolePermission(RoleEntity roleEntity,Set<Long> newPermissionIds){
		 //清除原先角色权限的关系
		 rolePermissionMapper.deleteByRoleId(roleEntity.getId());
		 //新增现有关系
		 if(CollectionUtils.isNotEmpty(newPermissionIds)){
   		  for(Long permissionId : newPermissionIds){
   			   RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
   			   rolePermissionEntity.setCreateTime(new Date());
   			   PermissionEntity entity = permissionMapper.selectByPrimaryKey(permissionId);
   			   if(entity !=null){
   				   rolePermissionEntity.setPermissionCode(entity.getPermissionCode());
   			   }
   			   rolePermissionEntity.setPermissionId(permissionId);
   			   rolePermissionEntity.setRoleId(roleEntity.getId());
   			   rolePermissionEntity.setRoleName(roleEntity.getRoleName());
   			   rolePermissionMapper.insert(rolePermissionEntity);
   		  }
   	  }
	}
}
