package com.kittydaddy.service.system.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.facade.dto.system.RoleDto;
import com.kittydaddy.metadata.system.dao.RoleEntityMapper;
import com.kittydaddy.metadata.system.domain.RoleEntity;
import com.kittydaddy.service.system.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
      
	@Autowired
	private RoleEntityMapper roleEntityMapper;

	@Override
	public PageInfo<RoleEntity> queryRolesByPage(String name, String tenantId,Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex,pageSize, true, null, true);
		List<RoleEntity> list = roleEntityMapper.queryRolesByName(name, tenantId);
		PageInfo<RoleEntity> page = new PageInfo<RoleEntity>(list);
		return page;
	}

	@Override
	public PageInfo<RoleEntity> queryRolesByPage(Map<String, Object> params, String tenantId) {
		Integer pageIndex =  (Integer) params.get("pageIndex")==null?1:(Integer) params.get("pageIndex");
		Integer pageSize = (Integer)params.get("pageSize")==null?10:(Integer)params.get("pageSize");
		PageHelper.startPage(pageIndex,pageSize, true, null, true);
		List<RoleEntity> list = roleEntityMapper.queryRolesByTeanantId(tenantId);
		PageInfo<RoleEntity> page = new PageInfo<RoleEntity>(list);
		return page;
	}

	@Override
	public RoleEntity queryRolesById(String id) {
		RoleEntity roleEntity = roleEntityMapper.selectByPrimaryKey(id);
		return roleEntity;
	}

	@Override
	public void saveUpdateRole(Map<String, Object> params) {
		if(null == params.get("id")) {
			RoleEntity roleEntity = new RoleEntity();
			roleEntity.setRoleCode(params.get("roleCode")==null?null:params.get("roleCode").toString());
			roleEntity.setDescription(params.get("description")==null?null:params.get("description").toString());
			roleEntity.setRoleName(params.get("roleName")==null?null:params.get("roleName").toString());
			roleEntity.setTenantId(params.get("tenantId")==null?null:params.get("tenantId").toString());
			roleEntity.setCreateTime(new Date());
			roleEntityMapper.insert(roleEntity);
			
		}else{
			RoleEntity roleEntity = roleEntityMapper.selectByPrimaryKey(params.get("id").toString());
			roleEntity.setRoleCode(params.get("roleCode")==null?null:params.get("roleCode").toString());
			roleEntity.setDescription(params.get("description")==null?null:params.get("description").toString());
			roleEntity.setRoleName(params.get("roleName")==null?null:params.get("roleName").toString());
			roleEntity.setTenantId(params.get("tenantId")==null?null:params.get("tenantId").toString());
			roleEntity.setUpdateTime(new Date());
			roleEntityMapper.updateByPrimaryKey(roleEntity);
			
		}
	}

	@Override
	public void deleteById(String roleId) {
		roleEntityMapper.deleteByPrimaryKey(roleId);
	}
}
