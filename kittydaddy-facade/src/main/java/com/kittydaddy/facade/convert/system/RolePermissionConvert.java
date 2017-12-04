package com.kittydaddy.facade.convert.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.kittydaddy.facade.dto.system.RolePermissionDto;
import com.kittydaddy.metadata.system.domain.RolePermissionEntity;

/**
 * @author kitty daddy
 * 角色权限转换
 */
public class RolePermissionConvert {
    
	/**
	 * 实体类转dto
	 * @param entities
	 * @return
	 */
	public static List<RolePermissionDto> convertEntity2Dto(List<RolePermissionEntity> entities) {
		List<RolePermissionDto> rolePermissionDtos = null;
		if(CollectionUtils.isNotEmpty(entities)){
			rolePermissionDtos = new ArrayList<RolePermissionDto>();
			for(RolePermissionEntity rolePermissionEntity : entities){
				RolePermissionDto dto = new RolePermissionDto();
				BeanUtils.copyProperties(rolePermissionEntity, dto);
				rolePermissionDtos.add(dto);
			}
		}
		return rolePermissionDtos;
	}
}
