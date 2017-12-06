package com.kittydaddy.facade.convert.system;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.kittydaddy.facade.dto.system.LeftMenusDto;
import com.kittydaddy.facade.dto.system.PermissionDto;
import com.kittydaddy.facade.dto.system.PermissionTreeDto;
import com.kittydaddy.facade.dto.system.request.PermissionRequest;
import com.kittydaddy.facade.dto.system.response.PermissionTreeResponse;
import com.kittydaddy.metadata.system.domain.PermissionEntity;

/**
 * @author kitty daddy
 * 权限转换器
 */
public class PermissionConvert {
    
	/**
	 * request转换成实体类
	 * @param request
	 * @return
	 */
	public static PermissionEntity convertRequest2Entity(PermissionRequest request) {
		PermissionEntity entity = null;
		if(request!=null){
			entity = new PermissionEntity();
			BeanUtils.copyProperties(request, entity);
		}
		return entity;
	}
    
	/**
	 * 权限实体类转换成左侧菜单
	 * @param permissionEntity
	 * @param leftMenusDto
	 * @return
	 */
	public static LeftMenusDto convert2LeftMeusDto(PermissionEntity permissionEntity,LeftMenusDto leftMenusDto){
		 if(leftMenusDto == null) leftMenusDto = new LeftMenusDto();
		 leftMenusDto.setHref(permissionEntity.getPermissionUrl());
		 leftMenusDto.setIcon(permissionEntity.getPermissionICO());
		 leftMenusDto.setSpread(false);
		 leftMenusDto.setTitle(permissionEntity.getModuleName());
		 return leftMenusDto;
	}
	
	
	/**
	 * 将entity转换成response
	 * @param topEntity
	 * @param childEntitys
	 * @return
	 */
	public static PermissionTreeResponse convertEntity2TreeResponse(PermissionEntity topEntity, List<PermissionEntity> childEntitys) {
		PermissionTreeResponse response = new PermissionTreeResponse();
//		if(topEntity!=null){
//			response.setId(topEntity.getId());
//			response.setPermissionICO(topEntity.getPermissionICO());
//			response.setPermissionName(topEntity.getModuleName());
//			response.setPermissionUrl(topEntity.getPermissionUrl());
//		}
//		
//		//组装子节点
//		List<PermissionTreeResponse> childs = null;
//		if(CollectionUtils.isNotEmpty(childEntitys)){
//			childs = new ArrayList<PermissionTreeResponse>();
//			for(PermissionEntity entity : childEntitys){
//				PermissionTreeResponse child = new PermissionTreeResponse();
//				child.setId(topEntity.getId());
//				child.setPermissionICO(entity.getPermissionICO());
//				child.setPermissionName(entity.getModuleName());
//				child.setPermissionUrl(entity.getPermissionUrl());
//				childs.add(child);
//			}
//		}
//		response.setChild(childs);
		return response;
	}
    
	
	/**
	 * 实体类转换成权限dto
	 * @param entitys
	 * @return
	 */
	public static List<PermissionDto> convertEntitys2Dtos(List<PermissionEntity> entitys) {
		List<PermissionDto> permissionDtos = null;
		if(CollectionUtils.isNotEmpty(entitys)){
			 permissionDtos = new ArrayList<PermissionDto>();
			 for(PermissionEntity entity : entitys){
				 PermissionDto permissionDto = new PermissionDto();
				 BeanUtils.copyProperties(entity, permissionDto);
				 permissionDtos.add(permissionDto);
			 }
		}
		return permissionDtos;
	}

	/**
	 * 
	 * @param oldEntity 老的实体类
	 * @param request 容器dto
	 * @return
	 */
	public static PermissionEntity convertRequest2Entity(PermissionEntity oldEntity, PermissionRequest request) {
		if(oldEntity!=null){
			Date createTime = oldEntity.getCreateTime();
			Integer status = oldEntity.getStatus();
			BeanUtils.copyProperties(request, oldEntity);
			oldEntity.setCreateTime(createTime);
		    oldEntity.setStatus(status);
		}
		return oldEntity;
	}

	/**
	 * 实体类转成dto
	 * @param entities
	 * @return
	 */
	public static List<PermissionTreeDto> convertEntity2TreeDto(List<PermissionEntity> entities) {
		List<PermissionTreeDto> treeDtos = null;
        if(CollectionUtils.isNotEmpty(entities)){
        	treeDtos = new ArrayList<PermissionTreeDto>();
        	for(PermissionEntity entity : entities){
        		PermissionTreeDto dto = new PermissionTreeDto();
//        		if(entity.getParentId()==null || entity.getParentId()==0l){
//        			dto.setParentId("#");
//        		}else{
//        			dto.setParentId(entity.getParentId().toString());
//        		}
        		dto.setId(entity.getId().toString());
        		dto.setPermissionName(entity.getModuleName());
        		treeDtos.add(dto);
        	}
        }
		return treeDtos;
	}
    
}
