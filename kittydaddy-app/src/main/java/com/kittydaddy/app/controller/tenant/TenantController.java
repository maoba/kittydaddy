package com.kittydaddy.app.controller.tenant;
import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
//import com.kittydaddy.common.utils.IdSplitUtil;
import com.kittydaddy.facade.dto.tenant.TenantDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantRequest;
import com.kittydaddy.facade.dto.tenant.responsedto.BaseResponse;
import com.kittydaddy.facade.dto.tenant.responsedto.PageResponse;
import com.kittydaddy.metadata.system.domain.RoleEntity;
import com.kittydaddy.metadata.tenant.domain.TenantEntity;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.tenant.TenantService;

/**
 * 
 * @author kitty daddy
 * 租户管理（提供给平台管理员）
 */
@RestController
@RequestMapping(value="/tenant")
public class TenantController {
	@Autowired
	private TenantService tenantService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="roleList")
    public PageInfo<TenantEntity> queryTenantList(String name,Integer pageIndex,Integer pageSize, @CurrentUser CurrentUserInfo currentUser){
  	   PageInfo<TenantEntity> tenants = tenantService.queryTenantsByPage(name,currentUser.getTenantId(),pageIndex,pageSize);
  	   return tenants;
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
//		tenantService.delete(IdSplitUtil.splitString2Long(ids));
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
