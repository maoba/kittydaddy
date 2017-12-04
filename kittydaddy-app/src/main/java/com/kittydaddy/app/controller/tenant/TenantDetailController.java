package com.kittydaddy.app.controller.tenant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kittydaddy.facade.dto.tenant.TenantDetailDto;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantDetailRequest;
import com.kittydaddy.facade.dto.tenant.responsedto.BaseResponse;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.tenant.TenantDetailService;

/**
 * @author kitty daddy
 * 租户详情管理（提供给租户使用）
 */
@RestController
@RequestMapping(value="/tenant/pc/tenantDetail")
public class TenantDetailController {
	@Autowired
	private TenantDetailService tenantDetailService;
	
	/**
     * 保存租户的信息
     * @param request
     * @param currentUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save")
	@ResponseBody
	public BaseResponse saveTenant(TenantDetailRequest request,@CurrentUser CurrentUserInfo currentUser){
    	//保存租户信息
    	tenantDetailService.saveTenantDetail(request);
        return BaseResponse.getSuccessResponse(new Date());
    }
    
    /**
     * 根据Id查询租户的详情
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/queryTenantDetail")
    @ResponseBody
    public BaseResponse queryTenantDetail(@RequestParam(value="id", required=false) Long id){
    	 TenantDetailDto dto = tenantDetailService.queryTenantDetailById(id);
    	 return BaseResponse.getSuccessResponse(new Date(), dto);
    }
    
}
