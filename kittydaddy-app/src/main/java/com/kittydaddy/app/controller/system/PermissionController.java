package com.kittydaddy.app.controller.system;
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
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.metadata.system.domain.PermissionEntity;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.system.PermissionService;
/**
 * @author kitty daddy
 * 权限控制
 */
@RestController
@RequestMapping(value="/permission")
public class PermissionController extends BaseController{
    @Autowired
    private PermissionService permissionService;
    
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
     * 获取当前用户已经拥有的权限
     * @param currentUserInfo
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="permissionTreeCheckedList")
    public List<Map<String,Object>> permissionTreeCheckedList(@RequestParam String roleId){
    	List<Map<String,Object>> list = permissionService.permissionTreeCheckedList(roleId);
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
    
    /**
     * 查询列表
     * @param name
     * @param permissionType
     * @param pageIndex
     * @param pageSize
     * @param currentUser
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="permissionList")
    public BaseResponse queryPermissionList(String name,String permissionType, Integer pageIndex,Integer pageSize, @CurrentUser CurrentUserInfo currentUser){
 	   PageInfo<PermissionEntity> permissionPage = permissionService.queryPermissionsByPage(name==null?"":name.trim(),permissionType,currentUser.getTenantId(),pageIndex,pageSize);
 	   return BaseResponse.getSuccessResp("查询成功",permissionPage.getTotal(),permissionPage.getList());
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
    public String saveUpdatePermission(@RequestParam Map<String,Object> params,@CurrentUser CurrentUserInfo currentUserInfo){
    try{
    	   params.put("tenantId", currentUserInfo.getTenantId());
 		   permissionService.saveUpdatePermission(params);   
 	   }catch(Exception e){
 		   return RESULT_FAILURE;
 	   }
 	   return RESULT_SUCCESS;
    }
    
    @RequestMapping(method=RequestMethod.GET,value="deletePermission")
    public String deletePermission(@RequestParam String permissionId){
    	 try{
    		//根据id删除权限
    		permissionService.deleteRelativeEntityById(permissionId);
    		
    	 }catch(Exception e){
    		return RESULT_FAILURE;
    	 }
    	    return RESULT_SUCCESS;
    }
    
    @RequestMapping(method=RequestMethod.GET,value="permissionEdit")
	public ModelAndView editPermission(@CurrentUser CurrentUserInfo currentUserInfo,@RequestParam String permissionId){
	    ModelAndView view = new ModelAndView();
	    PermissionEntity permissionEntity = permissionService.queryPermissionById(permissionId);
	    view.addObject("permission",permissionEntity);
	    view.setViewName("/page/system/permissionEdit");
	    return view;
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
    
}
