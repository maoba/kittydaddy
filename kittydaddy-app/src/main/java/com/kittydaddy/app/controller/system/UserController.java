package com.kittydaddy.app.controller.system;
import java.util.Date;
import com.kittydaddy.common.enums.LoginTypeEnum;
import com.kittydaddy.facade.dto.system.request.UserRequest;
import com.kittydaddy.service.system.UserService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kittydaddy.common.enums.TerminalTypeEnum;
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.security.service.SecurityService;

/**
 * @author kitty daddy
 * 用户管理
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
	
	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;


	/**
	 * 保存用户
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/save", consumes = "application/json")
	@ResponseBody
	public BaseResponse saveUser(@RequestBody UserRequest request){
		//获取相关的盐
		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		String salt = randomNumberGenerator.nextBytes().toHex();
		request.setSalt(salt);
		request.setTerminalType(TerminalTypeEnum.TERMINAL_PC.getValue());

		//设置租户 id
		request.setTenantId(request.getTenantId());
		Integer loginType = request.getLoginType();

		//邮箱方式注册设置加密密码
		if(LoginTypeEnum.SYSTEM_EMAIL_LOGIN.getType() == loginType){
			request.setUserPwd(this.getEncryptPassword(salt, request.getUserPwd(), null, request.getEmail()));

			//手机方式注册设置加密密码
		}else if(LoginTypeEnum.SYSTEM_CELLPHONE_LOGIN.getType() == loginType){
			request.setUserPwd(this.getEncryptPassword(salt, request.getUserPwd(), null, request.getCellPhoneNum()));
		}

		request.setCreateTime(new Date());
		//保存入库
		userService.saveUser(request);
		return BaseResponse.getSuccessResponse(new Date());
	}
}
