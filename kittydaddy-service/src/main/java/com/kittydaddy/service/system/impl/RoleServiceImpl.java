package com.kittydaddy.service.system.impl;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.facade.convert.system.RoleConvert;
import com.kittydaddy.facade.dto.system.RoleDto;
import com.kittydaddy.facade.dto.system.request.RoleRequest;
import com.kittydaddy.metadata.system.dao.RoleEntityMapper;
import com.kittydaddy.metadata.system.domain.RoleEntity;
import com.kittydaddy.service.system.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
      
	@Autowired
	private RoleEntityMapper roleEntityMapper;

	@Override
	@Transactional
	public void saveRole(RoleRequest request) {
        RoleEntity entity = RoleConvert.convertRequest2Entity(request);		
        entity.setCreateTime(new Date());
        roleEntityMapper.insert(entity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<RoleDto> queryRolesByPage(String name, Long tenantId,
			Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize, true, null, true);
		List<RoleEntity> entitys = roleEntityMapper.queryRolesByName(name,tenantId);
		PageInfo pageInfo = new PageInfo(entitys);
		List<RoleDto> roleDtos = RoleConvert.convertEntitys2Dtos(entitys);
		pageInfo.setList(roleDtos);
		return pageInfo;
	}

	@Override
	@Transactional
	public void delete(Set<Long> ids) {
		 if(CollectionUtils.isNotEmpty(ids)){
			 for(Long id : ids){
				 roleEntityMapper.deleteByPrimaryKey(id);
			 }
		 } 
	}

	@Override
	public void update(RoleRequest request) {
         RoleEntity entity = roleEntityMapper.selectByPrimaryKey(request.getId());
         entity = RoleConvert.convertRequest2Entity(request,entity);
         roleEntityMapper.updateByPrimaryKey(entity);
	}

	@Override
	public List<RoleDto> queryRolesByTenantId(Long tenantId) {
		List<RoleEntity> entities = roleEntityMapper.queryRolesByTeanantId(tenantId);
		List<RoleDto> roleDtos = RoleConvert.convertEntitys2Dtos(entities);
		return roleDtos;
	}
}
