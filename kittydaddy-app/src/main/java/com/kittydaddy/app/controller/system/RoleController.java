package com.kittydaddy.app.controller.system;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.metadata.system.domain.RoleEntity;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.system.RolePermissionService;
import com.kittydaddy.service.system.RoleService;
import com.kittydaddy.service.system.UserRoleService;
/**
 * @author kitty daddy
 *  pc端角色管理controller
 */
@RestController
@RequestMapping(value="/role")
public class RoleController extends BaseController{
	  /**
	   * 角色服务
	   */
       @Autowired
   	   private RoleService roleService;
       /**
        * 用户角色服务
        */
       @Autowired
       private UserRoleService userRoleService;
       /**
        * 角色权限服务
        */
       @Autowired
       private RolePermissionService rolePermissionService;
       
       @RequestMapping(method=RequestMethod.GET,value="roleList.html")
       public ModelAndView queryRolesByPage(){
    	   ModelAndView view = new ModelAndView();
           view.setViewName("/page/system/roleList");
           return view;
       }
       
       @RequestMapping(method=RequestMethod.POST,value="roleList")
       public PageInfo<RoleEntity> queryRolesList(String name,Integer pageIndex,Integer pageSize, @CurrentUser CurrentUserInfo currentUser){
    	   PageInfo<RoleEntity> rolePage = roleService.queryRolesByPage(name,currentUser.getTenantId(),pageIndex,pageSize);
    	   return rolePage;
       }
       
       @RequestMapping(method=RequestMethod.POST,value="saveRolePermission")
       public String saveRolePermission(@RequestParam Map<String,Object> params){
    	   try{
    		   rolePermissionService.saveRolePermission(params);   
    	   }catch(Exception e){
    		   return RESULT_FAILURE;
    	   }
    	   return RESULT_SUCCESS;
       }
       
       @RequestMapping(method=RequestMethod.GET,value="roleEdit")
       public ModelAndView roleEdit(@RequestParam(value="roleId") String id){
    	   ModelAndView view = new ModelAndView();
    	   RoleEntity roleEntity = roleService.queryRolesById(id);
    	   view.addObject("roleInfo", roleEntity);
    	   view.setViewName("/page/system/roleEdit");
    	   return view;
       }
       
       @RequestMapping(method=RequestMethod.GET,value="roleAdd")
       public ModelAndView roleAdd(){
    	   ModelAndView view = new ModelAndView();
    	   view.setViewName("/page/system/roleAdd");
    	   return view;
       }
       
       @RequestMapping(method=RequestMethod.POST,value="saveUpdateRole")
       public String saveUpdateRole(@RequestParam Map<String,Object> params,@CurrentUser CurrentUserInfo currentUserInfo){
    	   //获取并设置租户的Id
    	   params.put("tenantId", currentUserInfo.getTenantId());
    	   roleService.saveUpdateRole(params);
    	   return RESULT_SUCCESS;
       }
       
       @RequestMapping(method=RequestMethod.GET,value="deleteRole")
       public String deleteRole(@RequestParam String roleId){
    	   try{
    		   //删除角色
        	   roleService.deleteById(roleId);
        	   //删除角色用户关系
        	   userRoleService.deleteByRoleId(roleId);
        	   //删除角色权限关系
        	   rolePermissionService.deleteByRoleId(roleId);
    	   }catch(Exception e){
    		   return RESULT_FAILURE;
    	   }
    	   return RESULT_SUCCESS;
       }
}
