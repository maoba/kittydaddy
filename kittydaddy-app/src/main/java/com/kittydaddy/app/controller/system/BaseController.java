package com.kittydaddy.app.controller.system;
import com.kittydaddy.common.utils.KStringUtils;
import com.kittydaddy.facade.dto.system.request.UserLoginRequest;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.security.service.SecurityService;
import com.kittydaddy.security.util.PublicKeyMap;
import com.kittydaddy.security.util.RSAUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author kitty daddy
 * 父类controller
 */
@Controller
@RequestMapping(value="/")
public class BaseController {
	
	 public static String RESULT_SUCCESS = "success";
	 
	 public static String RESULT_FAILURE = "failure";
	 
	 @Autowired
	 private SecurityService securityService;
	 
    /**
     * 跳转到登陆页面
     * @return
     */
    @RequestMapping(value = "/login.html",method = RequestMethod.GET)
    public ModelAndView login() {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("login");
        return modelAndView;
    }
    
    /**
     * 登出
     * @param currentUserInfo
     * @return
     */
    @RequestMapping(value="/logout",method=RequestMethod.GET) 
    @RequiresAuthentication
    public String logout(){
		securityService.logout();
		return "redirect:/login.html";
    } 
    
    /**
     * pc端后台进行登入
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping(method = RequestMethod.POST, value = "/login.do")
	public ModelAndView login(UserLoginRequest request)throws Exception {
		ModelAndView mav = new ModelAndView();//后期考虑加密问题
//		String pass = URLDecoder.decode(request.getPassword(), "UTF-8");
		//反转出前端的实际密码
//		String password = RSAUtils.decryptStringByJs(pass); 
//		if(StringUtils.isNotEmpty(RSAUtils.decryptStringByJs(pass))) request.setPassword(password);
		//进行Pc端登入
	    Session session = securityService.login(request);
		if(session==null || KStringUtils.isEmpty(session.getId().toString())){
			mav.addObject("errorMessage","用户名或密码错误");
            mav.setViewName("login");
            return mav;
		}
		mav.addObject("currentUserName", session.getAttribute("userName"));
		mav.setViewName("index");
		return mav;
	} 
    
    
//    /**
//	 * 保存用户
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(method = RequestMethod.POST, value = "/save", consumes = "application/json")
//	@ResponseBody
//	public BaseResponse saveUser(@RequestBody UserRequest request){
//		//获取相关的盐
//		request.setSalt(KCryptogramUtil.getSalt());
//		request.setTerminalType(TerminalTypeEnum.TERMINAL_PC.getValue());
//
//		//设置租户 id
//		request.setTenantId(request.getTenantId());
//		Integer loginType = request.getLoginType();
//
//		//邮箱方式注册设置加密密码
//		if(LoginTypeEnum.SYSTEM_EMAIL_LOGIN.getType() == loginType){
//			request.setUserPwd(KCryptogramUtil.getEncryptPassword(request.getSalt(),request.getUserPwd(), null, request.getEmail()));
//
//			//手机方式注册设置加密密码
//		}else if(LoginTypeEnum.SYSTEM_CELLPHONE_LOGIN.getType() == loginType){
//			request.setUserPwd(KCryptogramUtil.getEncryptPassword(request.getSalt(),request.getUserPwd(), request.getCellPhoneNum(), null));
//		}
//
//		request.setCreateTime(new Date());
//		//保存入库
////		userService.saveUser(request);
//		return BaseResponse.getSuccessResponse(new Date());
//	}
	
    /**
     * RSA加密[由服务器生成相关的私钥]
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,value="/keyPair.do")
    public @ResponseBody PublicKeyMap keyPair() throws Exception{
        PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
        return publicKeyMap;
    }
    
    /**
     * 跳转到主页面
     * @param map
     * @return
     */
    @RequestMapping(value="main.do",method = RequestMethod.GET)
    public ModelAndView main(){
    	ModelAndView view = new ModelAndView();
    	view.setViewName("/main");
    	return view;
    }
    
    
    /**
     * 跳转到修改密码的页面
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="modifyPassWord")
    public ModelAndView modifyPassWord(@CurrentUser CurrentUserInfo currentUserInfo){
 	   ModelAndView view = new ModelAndView();
 	   view.addObject("currentUserName", currentUserInfo.getUserName());
 	   view.addObject("currentUserId",currentUserInfo.getUserId());
 	   view.setViewName("/page/system/changePwd");
 	   return view;
    }
}
