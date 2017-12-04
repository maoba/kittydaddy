package com.kittydaddy.service.tenant;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.facade.dto.tenant.TenantTeacherDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TeacherRequest;
import com.kittydaddy.facade.dto.tenant.responsedto.app.TeacherDetailResponse;

/**
 * @author kitty daddy
 *  租户教师服务
 */
public interface TenantTeacherService {
    
	/**
	 * 根据条件进行查询
	 * @param name  教师的名称
	 * @param nickName  教师的昵称
	 * @param tenantId  租户的Id
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<TenantTeacherDto> queryTeachersByPage(String name, String nickName,Long tenantId, Integer pageIndex, Integer pageSize);
    
	/**
	 * 保存教师信息
	 * @param request
	 */
	void saveTeacher(TeacherRequest request);
    
	/**
	 * 删除操作
	 * @param ids
	 */
	void delete(Set<Long> ids);
    
	/**
	 * 更新教师信息
	 * @param request
	 */
	void updateTeacher(TeacherRequest request);
    
	/**
	 * 根据Id查询教师的详情信息
	 * @param id
	 * @return
	 */
	TeacherDetailResponse queryTeacherById(Long id);
}
