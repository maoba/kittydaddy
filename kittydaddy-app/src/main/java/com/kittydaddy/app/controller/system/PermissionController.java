package com.kittydaddy.app.controller.system;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kittydaddy.common.utils.IdSplitUtil;
import com.kittydaddy.facade.dto.system.LeftMenusDto;
import com.kittydaddy.facade.dto.system.PermissionDto;
import com.kittydaddy.facade.dto.system.request.RolePermissionRequest;
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.facade.dto.tenant.requestdto.TenantPermissionRequest;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.system.PermissionService;
import com.kittydaddy.service.system.RolePermissionService;
import com.kittydaddy.service.system.UserRoleService;
/**
 * @author kitty daddy
 * 权限控制
 */
@RestController
@RequestMapping(value="/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    
//    /**
//     * 获取当前用户的权限树
//     * @param currentUserInfo 当前用户信息
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.GET, value = "/queryPermissionTree",produces = "application/json")
//    @ResponseBody
//    public Map<String,Object> queryPermissionTree(@CurrentUser CurrentUserInfo currentUserInfo){
//    	Map<String,Object> map = new HashMap<String,Object>();
//    	//根据用户的Id以及租户的id查询权限信息
//    	List<PermissionTreeResponse> response = permissionService.queryPermissionTree(currentUserInfo.getUserId(),currentUserInfo.getTenantId());
//    	map.put("navs", response);
//    	return map;
//    }
    
    /**
     * 分页查询
     * @param name 模块名称
     * @param pageIndex 当前页码
     * @param pageSize 一页数量
     * @param currentUser 
     * @return
     */
//    @SuppressWarnings("static-access")
//	@RequestMapping(method=RequestMethod.GET,value="/queryPermissions")
//    @ResponseBody
//    public PageResponse queryPermissionsByPage(
//		@RequestParam(value="name", required=false) String name,
//        @RequestParam(value="pageIndex", required=false,defaultValue="0") Integer pageIndex,
//        @RequestParam(value="pageSize", required=false,defaultValue="0") Integer pageSize,@CurrentUser CurrentUserInfo currentUser){
//    	PageResponse pageResponse = new PageResponse();
//    	
////    	//根据模块名称，租户的id查询角色
////    	PageInfo<PermissionDto> permissionDtos = permissionService.queryPermissionByPage(name,currentUser.getTenantId(),pageIndex,pageSize);
//    	
//	    return pageResponse.getSuccessPage(permissionDtos);
//    }
      
    /**
     * 根据租户的Id查看租户开通的业务
     * @param tenantId
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryBandingFunctions")
    @ResponseBody
    public BaseResponse queryBandingFunctions(@RequestParam(value="tenantId",defaultValue="0") Long tenantId){
    	List<PermissionDto>  permissionDtos = permissionService.queryPermissionDtoByTenantId(tenantId);
    	return BaseResponse.getSuccessResponse(permissionDtos);
    }
    
    /**
     * 开通对应租户的权限
     * @param request
     * @param currentUser
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/openPermission")
    @ResponseBody
    public BaseResponse openPermission(TenantPermissionRequest request,@CurrentUser CurrentUserInfo currentUser){
    	permissionService.openPermission(request);
    	return BaseResponse.getSuccessResponse(new Date());
    }
    
    /**
     * 获取左侧的菜单
     * @param currentUser
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryLeftMenus")
    @ResponseBody
    public List<LeftMenusDto> queryPermission(@CurrentUser CurrentUserInfo currentUser){
    	List<LeftMenusDto> leftMenus = permissionService.queryLeftMenus(currentUser.getUserId(),currentUser.getTenantId());
    	return leftMenus;
    }
    
    /**
     * 查找所有的目录
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryNormalCatalogs")
    @ResponseBody
    public BaseResponse queryNormalCatalogs(){
    	List<PermissionDto> catalogPermissionDtos = permissionService.queryCatalogPermissions();
    	return BaseResponse.getSuccessResponse(new Date(), catalogPermissionDtos);
    }
    
//    /**
//     * 查询当前租户下的所有的权限
//     * @param currentUserInfo
//     * @return
//     */
//    @RequestMapping(method=RequestMethod.GET,value="/queryCurrentTenantPermissions")
//    @ResponseBody
//    public BaseResponse queryPermissions(@CurrentUser CurrentUserInfo currentUserInfo){
//    	 List<PermissionTreeDto> permissionTreeDtos = permissionService.queryPermissionByTenantId(currentUserInfo.getTenantId());
//    	 return BaseResponse.getSuccessResponse(new Date(), permissionTreeDtos);
//    }
    
    
//    /**
//     * 保存权限信息
//     * @param request
//     * @param currentUser
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = "application/json")
//	@ResponseBody
//	public BaseResponse savePermission(PermissionRequest request,@CurrentUser CurrentUserInfo currentUser){
//    	//设置终端类型
//    	request.setTerminalType(TerminalTypeEnum.TERMINAL_PC.getValue());
//    	//设置租户Id
//    	request.setTenantId(currentUser.getTenantId());
//    	permissionService.savePermission(request);
//        return BaseResponse.getSuccessResponse(new Date());
//    }
    
    /**
     * 保存角色权限关系
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="/saveRolePermission")
    @ResponseBody
    public BaseResponse saveRolePermission(RolePermissionRequest request){
//    	rolePermissionService.saveRolePermission(request);
    	return BaseResponse.getSuccessResponse(new Date());
    }
    
    
//    /**
//     * 对权限进行更新
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.POST , value = "update")
//    @ResponseBody
//    public BaseResponse updatePermission(PermissionRequest request,@CurrentUser CurrentUserInfo currentUserInfo){
//    	request.setTenantId(currentUserInfo.getTenantId());
//    	permissionService.updatePermission(request);
//    	return BaseResponse.getSuccessResponse(new Date());
//    }
//    
    
    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "delete")
    public BaseResponse deletePermission(@RequestParam(value="ids") String ids){
    	//删除权限
    	permissionService.delete(IdSplitUtil.splitString2Long(ids));
    	//删除角色权限关系
//    	rolePermissionService.deleteByPermissionIds(IdSplitUtil.splitString2Long(ids));
    	
    	return BaseResponse.getSuccessResponse(new Date());
    }
    
    
    
    
    /**
     * 查询某个角色已经绑定过的角色
     * @param roleId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/queryBandingPermissions",produces = "application/json")
    @ResponseBody
    public BaseResponse queryBandingPermissions(@RequestParam(value="roleId") Long roleId){
//    	List<RolePermissionDto> rolePermissionDtos = rolePermissionService.queryRolePermissionByRoleId(roleId);
    	List<Long> ids = new ArrayList<Long>();
//    	if(CollectionUtils.isNotEmpty(rolePermissionDtos)){
//    		for(RolePermissionDto dto : rolePermissionDtos){
//    			ids.add(dto.getPermissionId());
//    		}
//    	}
    	return BaseResponse.getSuccessResponse(new Date(), ids);
    }
    
    /**
     * 查询某个租户下用户的权限
     */
    @RequestMapping(method = RequestMethod.GET,value="/queryTenantPermissions",produces = "application/json")
    @ResponseBody
    public BaseResponse queryTenantPermissions(@RequestParam(value="tenantId") Long tenantId,@RequestParam(value="userId",defaultValue="0") Long userId){
//    	List<UserRoleDto> userRoles = userRoleService.queryBandingRoles(userId, tenantId);
//    	
//    	List<UserRoleEntity> userRoleEntities = UserRoleConvert.convertDto2Entity(userRoles);
//    	Set<RolePermissionEntity> rolePermissionEntities = rolePermissionService.queryRolePermissionEntity(userRoleEntities);
//    	List<Long> ids = new ArrayList<Long>();
//    	if(CollectionUtils.isNotEmpty(rolePermissionEntities)){
//    		for(RolePermissionEntity entity : rolePermissionEntities){
//    			ids.add(entity.getPermissionId());
//    		}
//    	}
//    	return BaseResponse.getSuccessResponse(new Date(), ids);
    	return null;
    }
    
    
    
    
}
