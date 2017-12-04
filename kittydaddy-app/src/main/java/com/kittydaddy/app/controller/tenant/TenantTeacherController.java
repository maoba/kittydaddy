package com.kittydaddy.app.controller.tenant;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.utils.IdSplitUtil;
import com.kittydaddy.facade.dto.tenant.TenantTeacherDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TeacherRequest;
import com.kittydaddy.facade.dto.tenant.responsedto.BaseResponse;
import com.kittydaddy.facade.dto.tenant.responsedto.PageResponse;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.tenant.TenantTeacherService;

/**
 * @author kitty daddy
 * 教师管理
 */
@RestController
@RequestMapping(value="/tenant/pc/tenantTeacher")
public class TenantTeacherController {
	
	@Autowired
	private TenantTeacherService tenantTeacherService; 
	
	/**
	 * 分页查询
	 * @param name  教师姓名
	 * @param nickName 教师昵称
	 * @param pageIndex
	 * @param pageSize
	 * @param currentUser
	 * @return
	 */
    @SuppressWarnings("static-access")
	@RequestMapping(method=RequestMethod.GET,value="/queryTeachersByPage",produces="application/json")
    @ResponseBody
    public PageResponse queryTeachersByPage(
		@RequestParam(value="name", required=false) String name,
		@RequestParam(value="nickName",required=false) String nickName,
        @RequestParam(value="pageIndex", required=false,defaultValue="0") Integer pageIndex,
        @RequestParam(value="pageSize", required=false,defaultValue="0") Integer pageSize,@CurrentUser CurrentUserInfo currentUser){
    	PageResponse pageResponse = new PageResponse();
    	
    	//根据租户的id以及相关的名称条件组合查询教师信息
    	PageInfo<TenantTeacherDto> tenantTeacherDtos = tenantTeacherService.queryTeachersByPage(name,nickName,currentUser.getTenantId(),pageIndex,pageSize);
	    return pageResponse.getSuccessPage(tenantTeacherDtos);
    }
    
    /**
     * 保存教师信息
     * @param request
     * @param currentUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save")
	@ResponseBody
	public BaseResponse saveTeacher(TeacherRequest request, @CurrentUser CurrentUserInfo currentUser){
    	//保存租户信息
    	request.setTenantId(currentUser.getTenantId());
    	tenantTeacherService.saveTeacher(request);
        return BaseResponse.getSuccessResponse(new Date());
    }
    
    
    /**
     * 更新教师的信息
     * @param request
     * @param currentUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="/update")
    @ResponseBody
    public BaseResponse updateTeacher(TeacherRequest request, @CurrentUser CurrentUserInfo currentUser){
    	//更新租户信息
    	request.setTenantId(currentUser.getTenantId());
    	tenantTeacherService.updateTeacher(request);
    	return BaseResponse.getSuccessResponse(new Date());
    }
    
    
    /**
   	 * 后台进行删除
   	 * @param ids
   	 * @return
   	 */
   	@RequestMapping(method=RequestMethod.GET,value="/delete")
   	@RequiresAuthentication//表示删除
   	public BaseResponse delete(@RequestParam(value="ids") String ids){
   		 //删除租户的信息(逻辑删除)
   		tenantTeacherService.delete(IdSplitUtil.splitString2Long(ids));
   		return BaseResponse.getSuccessResponse(new Date());
   	}
}
