package com.kittydaddy.facade.convert.tenant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import com.kittydaddy.facade.dto.tenant.TenantDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantRequest;
import com.kittydaddy.metadata.tenant.domain.TenantEntity;

/**
 * @author kitty daddy
 * 租户转换器
 */
public class TenantConvert {
    
	/**
	 * 实体类转换成dto
	 * @param tenantEntities
	 * @return
	 */
	public static List<TenantDto> convertEntity2Dto(List<TenantEntity> tenantEntities) {
		List<TenantDto> tenantDtos = null;
		if(CollectionUtils.isNotEmpty(tenantEntities)){
			tenantDtos = new ArrayList<TenantDto>();
			for(TenantEntity tenantEntity : tenantEntities){
				TenantDto tenantDto = new TenantDto();
				BeanUtils.copyProperties(tenantEntity, tenantDto);
				tenantDtos.add(tenantDto);
			}
		}
		return tenantDtos;
	}
    
	/**
	 * request转换成实体类
	 * @param request
	 * @return
	 */
	public static TenantEntity convertRequest2Entity(TenantRequest request) {
		TenantEntity tenantEntity =  null;
		if(request!=null){
			tenantEntity = new TenantEntity();
			BeanUtils.copyProperties(request, tenantEntity);
		}
		return tenantEntity;
	}
    
	/**
	 * 
	 * @param request
	 * @param oldTenantEntity
	 * @return
	 */
	public static TenantEntity converRequest2Entity(TenantRequest request,TenantEntity oldTenantEntity) {
		if(request!=null && oldTenantEntity!=null){
//             Long oldId = oldTenantEntity.getId();
//             BeanUtils.copyProperties(request, oldTenantEntity);
//             oldTenantEntity.setUpdateTime(new Date()); 
//             oldTenantEntity.setUpdateTime(new Date());
//             oldTenantEntity.setId(oldId);
		}
		return oldTenantEntity;
	}

}
