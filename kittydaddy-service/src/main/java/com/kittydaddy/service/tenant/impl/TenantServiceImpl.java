package com.kittydaddy.service.tenant.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.facade.convert.tenant.TenantConvert;
import com.kittydaddy.facade.dto.tenant.TenantDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantRequest;
import com.kittydaddy.metadata.tenant.dao.TenantEntityMapper;
import com.kittydaddy.metadata.tenant.domain.TenantEntity;
import com.kittydaddy.service.tenant.TenantService;
/**
 * @author kitty daddy
 * 租户后台业务
 */
@Service
public class TenantServiceImpl implements TenantService{
	@Autowired
	private TenantEntityMapper tenantMapper;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<TenantDto> queryTenantsByPage(Long tenantId,String name,Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize, true, null, true);
		List<TenantEntity> tenantEntities = null;
        if(tenantId ==null || tenantId == 1){
        	 tenantEntities = tenantMapper.queryTenantsByName(name);
        }else{
        	 if(tenantEntities == null) tenantEntities = new ArrayList<TenantEntity>();
//        	 TenantEntity tenantEntity = tenantMapper.selectByPrimaryKey(tenantId);
//        	 tenantEntities.add(tenantEntity);
        }
		PageInfo pageInfo = new PageInfo(tenantEntities);
		List<TenantDto> userDtos = TenantConvert.convertEntity2Dto(tenantEntities);
		pageInfo.setList(userDtos);
		return pageInfo;
	}

	@Override
	public void saveTenant(TenantRequest request) {
		TenantEntity tenantEntity = TenantConvert.convertRequest2Entity(request);
//		tenantEntity.setStatus(StatusEnum.NORMAL.getValue());
		tenantEntity.setCreateTime(new Date());
		tenantMapper.insert(tenantEntity);
	}

	@Override
	public void delete(Set<Long> ids) {
         if(CollectionUtils.isNotEmpty(ids)){
        	 for(Long id : ids){
//        		  TenantEntity tenantEntity = tenantMapper.selectByPrimaryKey(id);
//        		  if(tenantEntity != null){
//        			  tenantEntity.setStatus(StatusEnum.DELETE.getValue());
//        			  tenantMapper.updateByPrimaryKey(tenantEntity);
//        		  }
        	 }
         }		
	}

	@Override
	public void update(TenantRequest request) {
         if(request!=null){
//        	 TenantEntity oldTenantEntity = tenantMapper.selectByPrimaryKey(request.getId());
//        	 if(oldTenantEntity!=null){
//                  oldTenantEntity.setDescription(request.getDescription()); 
//                  oldTenantEntity = TenantConvert.converRequest2Entity(request,oldTenantEntity);
//                  oldTenantEntity.setStatus(StatusEnum.NORMAL.getValue());
//                  tenantMapper.updateByPrimaryKey(oldTenantEntity);
//        	 }
         }		
	}

	@Override
	public PageInfo<TenantEntity> queryTenantsByPage(String name, String tenantId, Integer pageIndex,Integer pageSize) {
		PageHelper.startPage(pageIndex,pageSize, true, null, true);
		List<TenantEntity> tenantList = tenantMapper.queryTenantsByName(name);
		PageInfo<TenantEntity> page = new PageInfo<TenantEntity>(tenantList);
		return page;
	}

}
