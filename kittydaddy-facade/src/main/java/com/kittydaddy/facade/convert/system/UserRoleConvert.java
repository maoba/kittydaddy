package com.kittydaddy.facade.convert.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.kittydaddy.facade.dto.system.UserRoleDto;
import com.kittydaddy.metadata.system.domain.UserRoleEntity;
/**
 * @author kitty daddy
 * 用户角色转换器
 */
public class UserRoleConvert {
    
	/**
	 * 用户角色实体类转换成dto
	 * @param entities
	 * @return
	 */
	public static List<UserRoleDto> convertEntity2Dto(List<UserRoleEntity> entities) {
		List<UserRoleDto> userRoleDtos = null;
		if(CollectionUtils.isNotEmpty(entities)){
			userRoleDtos = new ArrayList<UserRoleDto>();
			for(UserRoleEntity entity : entities){
				UserRoleDto userRoleDto = new UserRoleDto();
				BeanUtils.copyProperties(entity, userRoleDto);
				userRoleDtos.add(userRoleDto);
			}
		}
		return userRoleDtos;
	}
    
	/**
	 * 将dto转换成实体类
	 * @param userRoles
	 * @return
	 */
	public static List<UserRoleEntity> convertDto2Entity(List<UserRoleDto> userRoles) {
		if(CollectionUtils.isEmpty(userRoles)) return null;
		List<UserRoleEntity> userRoleEntities = new ArrayList<UserRoleEntity>();
		for(UserRoleDto userRoleDto : userRoles){
			UserRoleEntity userRoleEntity = new UserRoleEntity();
			BeanUtils.copyProperties(userRoleDto, userRoleEntity);
			userRoleEntities.add(userRoleEntity);
		}
		return userRoleEntities;
	}

}
