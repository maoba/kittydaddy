package com.kittydaddy.app.controller.system;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.service.system.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	
	@RequestMapping(method=RequestMethod.GET,value="userList.html")
    public ModelAndView queryRolesByPage(){
 	    ModelAndView view = new ModelAndView();
        view.setViewName("/page/system/userList");
        return view;
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
    
    @RequestMapping(method=RequestMethod.POST,value="saveUpdateRole")
    public String saveUpdateUser(@RequestParam Map<String,Object> params,@CurrentUser CurrentUserInfo currentUserInfo){
 	   //获取并设置租户的Id
 	   params.put("tenantId", currentUserInfo.getTenantId());
// 	   userService.saveUpdateUser(params);
 	   return RESULT_SUCCESS;
    }
	
}
