package com.kittydaddy.service.tenant;
import com.kittydaddy.facade.dto.tenant.TenantDetailDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantDetailRequest;

public interface TenantDetailService {
    
	/**
	 * 保存租户详细信息
	 * @param request
	 */
	void saveTenantDetail(TenantDetailRequest request);
    
	/**
	 * 根据id进行查询租户的详细的信息
	 * @param id
	 * @return
	 */
	TenantDetailDto queryTenantDetailById(Long id);

}
