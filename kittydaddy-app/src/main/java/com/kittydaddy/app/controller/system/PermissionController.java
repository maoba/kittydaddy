package com.kittydaddy.app.controller.system;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.facade.dto.system.LeftMenusDto;
import com.kittydaddy.facade.dto.system.PermissionDto;
import com.kittydaddy.facade.dto.system.request.RolePermissionRequest;
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.metadata.system.domain.PermissionEntity;
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
public class PermissionController extends BaseController{
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    /**
     * 跳转到树页面
     * @param roleId
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="permissionTree")
    public ModelAndView queryPermissionTree(@RequestParam String roleId){
 	    ModelAndView view = new ModelAndView();
 	    view.addObject("roleId",roleId);
        view.setViewName("/page/system/permissionTree");
        return view;
    }
    
    /**
     * 获取权限树列表
     * @param currentUserInfo
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="permissionTreeList")
    public List<Map<String,Object>> queryPermissionTreeList(@CurrentUser CurrentUserInfo currentUserInfo){
    	List<Map<String,Object>> list = permissionService.queryPermissionTreeList(currentUserInfo.getTenantId());
        return list;
    }
    
    /**
     * 跳转到权限页面
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="permissionList.html")
    public ModelAndView queryRolesByPage(){
 	   ModelAndView view = new ModelAndView();
        view.setViewName("/page/system/permissionList");
        return view;
    }
    
    
    @RequestMapping(method=RequestMethod.POST,value="permissionList")
    public PageInfo<PermissionEntity> queryPermissionList(String name,String permissionType, Integer pageIndex,Integer pageSize, @CurrentUser CurrentUserInfo currentUser){
 	   PageInfo<PermissionEntity> permissionPage = permissionService.queryPermissionsByPage(name==null?"":name.trim(),permissionType,currentUser.getTenantId(),pageIndex,pageSize);
 	   return permissionPage;
    }
    
    
    /**
     * 跳转到新增权限页面
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="permissionAdd")
    public ModelAndView toAddPermission(){
 	   ModelAndView view = new ModelAndView();
        view.setViewName("/page/system/permissionAdd");
        return view;
    }
    
    /**
     * 更新保存权限
     * @param params
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="saveUpdatePermission")
    public String saveUpdatePermission(Map<String,Object> params){
    try{
 		   permissionService.saveUpdatePermission(params);   
 	   }catch(Exception e){
 		   return RESULT_FAILURE;
 	   }
 	   return RESULT_SUCCESS;
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
    
    
    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "delete")
    public BaseResponse deletePermission(@RequestParam(value="ids") String ids){
    	//删除权限
//    	permissionService.delete(IdSplitUtil.splitString2Long(ids));
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
    
    
    
}
