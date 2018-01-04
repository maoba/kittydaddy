package com.kittydaddy.app.controller.system;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.service.system.UserRoleService;
import com.kittydaddy.service.system.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.kittydaddy.metadata.system.domain.UserEntity;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;

/**
 * @author kitty daddy
 * 用户管理
 */
@RestController
@RequestMapping(value="/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;

	
	@RequestMapping(method=RequestMethod.GET,value="userList.html")
    public ModelAndView queryRolesByPage(){
 	    ModelAndView view = new ModelAndView();
        view.setViewName("/page/system/userList");
        return view;
    }
	
	 /**
     * 校验密码是否正确
     * @param oldPassword
     * @return
     */
    @RequestMapping(value="/checkPassword",method=RequestMethod.GET)
    public String checkPassword(@RequestParam(value="oldPassword")String oldPassword,@CurrentUser CurrentUserInfo currentUserInfo){
    	boolean result = userService.checkPassword(oldPassword,currentUserInfo.getUserId());
    	if(result) return RESULT_SUCCESS;
    	return RESULT_FAILURE;
    }
	
	/**
	 * 查询用户列表
	 * @param name 姓名
	 * @param status 状态
	 * @param pageIndex
	 * @param pageSize
	 * @param currentUser
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="userList")
    public PageInfo<UserEntity> queryUsersList(String name,Integer status,Integer pageIndex,Integer pageSize, @CurrentUser CurrentUserInfo currentUser){
  	   PageInfo<UserEntity> userListPage = userService.queryUserByPage(name,currentUser.getTenantId(),status,pageIndex,pageSize);
  	   return userListPage;
    }

	
	@RequestMapping(method=RequestMethod.GET,value="userAdd")
    public ModelAndView roleAdd(){
 	   ModelAndView view = new ModelAndView();
 	   view.setViewName("/page/system/userAdd");
 	   return view;
    }
    
	/**
	 * 新增或者更新用户
	 * @param params
	 * @param currentUserInfo
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST,value="saveUpdateUser")
    public String saveUpdateUser(@RequestParam Map<String,Object> params,@CurrentUser CurrentUserInfo currentUserInfo){
 	   //获取并设置租户的Id
 	   params.put("tenantId", currentUserInfo.getTenantId());
 	   userService.saveUpdateUser(params);
 	   return RESULT_SUCCESS;
    }
    
    /**
     * 新增用户角色关系
     * @param params
     * @param currentUserInfo
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="saveUserRole")
    public Boolean saveUserRole(@RequestParam Map<String,Object> params,@CurrentUser CurrentUserInfo currentUserInfo){
    	boolean result = userService.saveUserRole(params);
    	return result;
    }
    
    
    /**
     * 跳转到角色选择页面
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="roleCheckList")
    public ModelAndView grantRole(@RequestParam String checkUserId){
 	   ModelAndView view = new ModelAndView();
 	   view.addObject("checkUserId", checkUserId);
 	   view.setViewName("/page/system/roleCheckList");
 	   return view;
    }
    
    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="deleteUser")
    public String deleteUser(@RequestParam String userId){
 	   try{
 		   //删除用户
 		   userService.delete(userId);
     	   //删除用户角色关系
     	   userRoleService.deleteByUserId(userId);
     	   
 	   }catch(Exception e){
 		   return RESULT_FAILURE;
 	   }
 	   return RESULT_SUCCESS;
    }
	
}
