package com.kittydaddy.facade.convert.tenant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.kittydaddy.facade.dto.tenant.TenantTeacherDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TeacherRequest;
import com.kittydaddy.facade.dto.tenant.responsedto.app.TeacherDetailResponse;
import com.kittydaddy.metadata.tenant.domain.TenantTeacherEntity;
/**
 * @author kitty daddy
 * 租户教师
 */
public class TenantTeacherConvert {
    
	/**
	 * 教师实体类转换成dto
	 * @param teacherEntities
	 * @return
	 */
	public static List<TenantTeacherDto> convertEntity2Dto(List<TenantTeacherEntity> teacherEntities) {
		List<TenantTeacherDto> tenantTeacherDtos = null;
		if(CollectionUtils.isNotEmpty(teacherEntities)){
			tenantTeacherDtos = new ArrayList<TenantTeacherDto>();
			
			for(TenantTeacherEntity teacherEntity : teacherEntities){
				TenantTeacherDto teacherDto = new TenantTeacherDto();
                BeanUtils.copyProperties(teacherEntity, teacherDto);
                tenantTeacherDtos.add(teacherDto);
			}
		}
		return tenantTeacherDtos;
	}
    
	/**
	 * 请求转换成实体类
	 * @param request
	 * @return
	 */
  	public static TenantTeacherEntity convertRequest2Entity(TeacherRequest request) {
  		TenantTeacherEntity entity = null;
  		if(request!=null){
  			entity = new TenantTeacherEntity();
  			BeanUtils.copyProperties(request, entity);
  		}
  		return entity;
	}
    
  	/**
  	 * 将request中的属性  更新到老的对象上面
  	 * @param request
  	 * @param oldTeacher
  	 * @return
  	 */
	public static TenantTeacherEntity convertRequest2Entity(TeacherRequest request, TenantTeacherEntity oldTeacher) {
	    Date createTime = oldTeacher.getCreateTime();
	    int status = oldTeacher.getStatus();
		BeanUtils.copyProperties(request, oldTeacher);
		oldTeacher.setCreateTime(createTime);
		oldTeacher.setStatus(status);
		oldTeacher.setTenantId(request.getTenantId());
		return oldTeacher;
	}

	/**
	 * 将实体类转换成response对象
	 * @param teacherDetail
	 * @return
	 */
	public static TeacherDetailResponse convertEntity2Response(TenantTeacherEntity teacherDetail) {
		TeacherDetailResponse response = null;
		if(teacherDetail != null){
			response = new TeacherDetailResponse();
			BeanUtils.copyProperties(teacherDetail, response);
		}
		return response;
	}
}
