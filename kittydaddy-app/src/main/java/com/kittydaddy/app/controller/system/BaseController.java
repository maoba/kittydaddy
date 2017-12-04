package com.kittydaddy.app.controller.system;

import com.kittydaddy.common.constant.TemplateConstants;
import com.kittydaddy.common.enums.EncryptionEnum;
import com.kittydaddy.facade.dto.system.request.UserLoginRequest;
import com.kittydaddy.facade.dto.system.response.PermissionTreeResponse;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.security.service.SecurityService;
import com.kittydaddy.security.util.PublicKeyMap;
import com.kittydaddy.security.util.RSAUtils;
import com.kittydaddy.service.system.PermissionService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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
	
	 @Autowired
	 private SecurityService securityService;
	 
	 @Autowired
	 private PermissionService permissionService;
    /**
     * 获取加密后的密码
     * @param salt 盐
     * @param password 明文密码
     * @param telephone 手机号码
     * @param email 邮箱
     * @return
     */
    public String getEncryptPassword(String salt,String password,String telephone,String email){
        if(StringUtils.isNotEmpty(telephone)){
            return new SimpleHash(EncryptionEnum.ALGORITHMNAME.getValue(),password,
                    ByteSource.Util.bytes(telephone + salt),Integer.parseInt(EncryptionEnum.HASHITERATIONS.getValue())).toHex();
        }else if(StringUtils.isNotEmpty(email)){
            return new SimpleHash(EncryptionEnum.ALGORITHMNAME.getValue(),password,
                    ByteSource.Util.bytes(email +salt),Integer.parseInt(EncryptionEnum.HASHITERATIONS.getValue())).toHex();
        }
        return null;
    }

    /**
     * 跳转到登陆页面
     * @return
     */
    @RequestMapping(value = "/login.html",method = RequestMethod.GET)
    public String login() {
        return TemplateConstants.LOGIN_TEMPLATES;
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
		String sessionId = securityService.login(request);
		if(StringUtils.isEmpty(sessionId)){
			mav.addObject("errorMessage","用户名或密码错误");
            mav.setViewName("login");
		}
		mav.setViewName("redirect:/index.do");
		return mav;
	} 
    
	 /**
     * 跳转到主页面
     * @param map
     * @return
     */
    @RequestMapping(value="/index.do",method = RequestMethod.GET)
    public ModelAndView layoutPage(@CurrentUser CurrentUserInfo currentUserInfo){
    	ModelAndView view = new ModelAndView();
        //根据用户的Id以及租户的id查询权限信息
        List<PermissionTreeResponse> menus = permissionService.queryMenus(currentUserInfo.getUserId(),currentUserInfo.getTenantId());
        view.addObject("menus",menus);
        return view;
    }
	
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
}
