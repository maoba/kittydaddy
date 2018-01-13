package com.kittydaddy.service.tenant.impl;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.facade.convert.tenant.TenantTeacherConvert;
import com.kittydaddy.facade.dto.tenant.TenantTeacherDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TeacherRequest;
import com.kittydaddy.facade.dto.tenant.responsedto.app.TeacherDetailResponse;
import com.kittydaddy.metadata.tenant.dao.TenantTeacherEntityMapper;
import com.kittydaddy.metadata.tenant.domain.TenantTeacherEntity;
import com.kittydaddy.service.tenant.TenantTeacherService;

/**
 * @author kitty daddy
 * 教师
 */
@Service
public class TenantTeacherServiceImpl implements TenantTeacherService{
	@Autowired
	private TenantTeacherEntityMapper teacherMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<TenantTeacherDto> queryTeachersByPage(String name,String nickName, Long tenantId, Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize, true, null, true);
		List<TenantTeacherEntity> teacherEntities = teacherMapper.queryTeachersByPage(name,nickName,tenantId);
		PageInfo pageInfo = new PageInfo(teacherEntities);
		List<TenantTeacherDto> teacherDtos = TenantTeacherConvert.convertEntity2Dto(teacherEntities);
		pageInfo.setList(teacherDtos);
		return pageInfo;
	}
    
	/**
	 * 保存教师信息
	 */
	@Override
	public void saveTeacher(TeacherRequest request) {
		TenantTeacherEntity entity = TenantTeacherConvert.convertRequest2Entity(request);
		//教师默认在职状态
//		entity.setStatus(StatusEnum.NORMAL.getValue());
		entity.setCreateTime(new Date());
        teacherMapper.insert(entity);	
	}
    
	
	/**
	 * 根据id删除实体类操作
	 */
	@Override
	public void delete(Set<Long> ids) {
        if(CollectionUtils.isNotEmpty(ids)){
        	for(Long id : ids){
        		TenantTeacherEntity entity = teacherMapper.selectByPrimaryKey(id);
        		if(entity != null){
        			entity.setStatus(StatusEnum.DELETE.getValue());
        			entity.setUpdateTime(new Date());
        			teacherMapper.updateByPrimaryKey(entity);
        		}
        	}
        }		
	}
    
	/**
	 * 更新教师
	 */
	@Override
	public void updateTeacher(TeacherRequest request) {
	      if(request !=null){
	    	   TenantTeacherEntity oldTeacher = teacherMapper.selectByPrimaryKey(request.getId());
	    	   if(oldTeacher !=null){
	    		   TenantTeacherEntity entity = TenantTeacherConvert.convertRequest2Entity(request,oldTeacher);
	    		   entity.setUpdateTime(new Date());
	    		   teacherMapper.updateByPrimaryKey(entity);
	    	   }
	      }	
	}
    
	/**
	 *  根据id进行
	 */
  	@Override
	public TeacherDetailResponse queryTeacherById(Long id) {
  		TenantTeacherEntity teacherDetail = teacherMapper.selectByPrimaryKey(id);
  		TeacherDetailResponse response = null;
  		if(teacherDetail != null){
  			 response = TenantTeacherConvert.convertEntity2Response(teacherDetail);
  		}
		return response;
	}
	
}
