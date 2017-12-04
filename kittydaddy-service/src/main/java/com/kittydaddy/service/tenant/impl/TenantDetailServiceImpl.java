package com.kittydaddy.service.tenant.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kittydaddy.facade.convert.tenant.TenantDetailConvert;
import com.kittydaddy.facade.dto.tenant.TenantDetailDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantDetailRequest;
import com.kittydaddy.metadata.tenant.dao.TenantDetailEntityMapper;
import com.kittydaddy.metadata.tenant.domain.TenantDetailEntity;
import com.kittydaddy.service.tenant.TenantDetailService;

@Service
public class TenantDetailServiceImpl implements TenantDetailService{
     @Autowired
     private TenantDetailEntityMapper tenantDetailMapper;

	 @Override
	 public void saveTenantDetail(TenantDetailRequest request) {
          TenantDetailEntity entity = TenantDetailConvert.convertRequest2Entity(request);
          tenantDetailMapper.insert(entity);
	 }

	@Override
	public TenantDetailDto queryTenantDetailById(Long id) {
		TenantDetailEntity entity = tenantDetailMapper.selectByPrimaryKey(id);
		TenantDetailDto dto = TenantDetailConvert.convertEntity2Dto(entity);
		return dto;
	}
}
