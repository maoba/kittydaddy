package com.kittydaddy.service.tenant;

import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.facade.dto.tenant.TenantDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantRequest;
import com.kittydaddy.metadata.tenant.domain.TenantEntity;

public interface TenantService {
    
	/**
	 * 
	 * @param name 租户的名称
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<TenantDto> queryTenantsByPage(Long tenantId,String name, Integer pageIndex, Integer pageSize);
     
	/**
	 * 保存租户的信息
	 * @param request
	 */
	void saveTenant(TenantRequest request);
    
	/**
	 * 根据id进行删除
	 */
	void delete(Set<Long> ids);
    
	/**
	 * 更新租户的信息
	 * @param request
	 */
	void update(TenantRequest request);
    
	/**
	 * 分页查询租户
	 * @param name
	 * @param tenantId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<TenantEntity> queryTenantsByPage(String name, String tenantId, Integer pageIndex, Integer pageSize);
     
}
