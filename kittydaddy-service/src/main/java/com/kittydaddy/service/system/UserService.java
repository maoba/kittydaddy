package com.kittydaddy.service.system;
import java.util.Map;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.facade.dto.system.UserDto;
import com.kittydaddy.facade.dto.system.request.UserRequest;
import com.kittydaddy.facade.dto.system.response.UserResponse;
import com.kittydaddy.metadata.system.domain.UserEntity;
/**
 * @author  maoba
 */
public interface UserService {
     /**
      * 根据用户的id进行查询
      * @param userId
      * @return
      */
	 UserResponse queryUserById(long userId);
     
	 /**
	  * 根据email地址以及终端类型进行查询
	  * @param email 邮箱地址
	  * @return
	  */
	 UserResponse queryUserByEmail(String email);
     
	 /**
	  * 根据手机号码以及终端类型进行查询
	  * @param cellPhoneNum 手机号码
	  * @return
	  */
	 UserResponse queryUserByCellPhone(String cellPhoneNum);
     
	 /**
	  * 分页查询用户
	  * @param name
	  * @param pageIndex
	  * @param pageSize
	  * @return
	  */
	 PageInfo<UserDto> queryUsersByPage(String name,Integer status,Long tenantId, Integer pageIndex,Integer pageSize);

	 /**
	  * 保存用户信息
	  * @param request
	  */
	 void saveUser(UserRequest request);
     
	 /**
	  * 更新用户
	  * @param response
	  */
	 void updateUser(UserResponse response);
     
	 /**
	  * 更新部分用户的信息
	  * @param request
	  */
	 void updateApartUserInfo(UserRequest request);

	 /**
	  * 根据name查询用户
	  * @param name
	  * @param tenantId
	  * @param pageIndex
	  * @param pageSize
	  * @return
	  */
	 PageInfo<UserEntity> queryUserByPage(String name, String tenantId,Integer status, Integer pageIndex, Integer pageSize);

	 
	 /**
	  * 新增用户
	  * @param params
	  */
	 void saveUpdateUser(Map<String, Object> params);

	 /**
	  * 建立用户角色的关系
	  * @param params
	  */
	 boolean saveUserRole(Map<String, Object> params);

	 /**
	  * 根据id删除用户
	  * @param id
	  */
	 void delete(String id);
     
	 /**
	  * 校验用户的密码是否正确
	  * @param oldPassword
	  * @param userId
	  * @return
	  */
	 boolean checkPassword(String oldPassword, String userId);
}
