package com.kittydaddy.facade.convert.tenant;

import org.springframework.beans.BeanUtils;

import com.kittydaddy.facade.dto.tenant.TenantDetailDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantDetailRequest;
import com.kittydaddy.metadata.tenant.domain.TenantDetailEntity;

/**
 * @author kitty daddy
 * 详细租户信息转换
 */
public class TenantDetailConvert {
	
	/**
	 * request转换成实体类
	 * @param request
	 * @return
	 */
	public static TenantDetailEntity convertRequest2Entity(TenantDetailRequest request) {
		TenantDetailEntity  entity = null;
		if(request!=null){
			 entity = new TenantDetailEntity();
			 BeanUtils.copyProperties(request, entity);
		}
		return entity;
	}
    
	/**
	 * 实体类转换成dto
	 * @param entity
	 * @return
	 */
	public static TenantDetailDto convertEntity2Dto(TenantDetailEntity entity) {
		TenantDetailDto dto = null;
		if(entity!=null){
			dto = new TenantDetailDto();
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

}
