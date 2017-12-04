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
import com.kittydaddy.facade.dto.tenant.TenantDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantRequest;
import com.kittydaddy.facade.dto.tenant.responsedto.BaseResponse;
import com.kittydaddy.facade.dto.tenant.responsedto.PageResponse;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.tenant.TenantService;

/**
 * 
 * @author kitty daddy
 * 租户管理（提供给平台管理员）
 */
@RestController
@RequestMapping(value="/tenant/pc/tenant")
public class TenantController {
	@Autowired
	private TenantService tenantService;
	
	 /**
     * 分页查询
     * @param name 租户的名称
     * @param pageIndex 当前页码
     * @param pageSize 一页数量
     * @param currentUser 
     * @return
     */
    @SuppressWarnings("static-access")
	@RequestMapping(method=RequestMethod.GET,value="/queryTenants")
    @ResponseBody
    public PageResponse queryTenantsByPage(
		@RequestParam(value="name", required=false) String name,
        @RequestParam(value="pageIndex", required=false,defaultValue="0") Integer pageIndex,
        @RequestParam(value="pageSize", required=false,defaultValue="0") Integer pageSize,@CurrentUser CurrentUserInfo currentUser){
    	PageResponse pageResponse = new PageResponse();
    	Long tenantId = null;
    	if(currentUser.getTenantId() != 1){
    		tenantId = currentUser.getTenantId();
    	}
    	//根据名称，租户的id查询角色
    	PageInfo<TenantDto> tenantDtos = tenantService.queryTenantsByPage(tenantId,name,pageIndex,pageSize);
	    return pageResponse.getSuccessPage(tenantDtos);
    }
    
    /**
     * 保存租户的信息
     * @param request
     * @param currentUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save")
	@ResponseBody
	public BaseResponse saveTenant(TenantRequest request,@CurrentUser CurrentUserInfo currentUser){
    	//保存租户信息
    	tenantService.saveTenant(request);
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
		tenantService.delete(IdSplitUtil.splitString2Long(ids));
		return BaseResponse.getSuccessResponse(new Date());
	}
	
	 /**
	  * 更新租户信息
	  * @param request
	  * @return
	  */
	 @RequestMapping(method = RequestMethod.POST, value = "/update")
	 public BaseResponse updateTenant(TenantRequest request){
		 tenantService.update(request);
		 return BaseResponse.getSuccessResponse(new Date());
	 }
}
