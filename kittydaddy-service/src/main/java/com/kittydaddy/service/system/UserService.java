package com.kittydaddy.service.system;
import java.util.Map;
import com.github.pagehelper.PageInfo;
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
	 UserEntity queryUserById(String userId);
     
	 /**
	  * 根据email地址以及终端类型进行查询
	  * @param email 邮箱地址
	  * @return
	  */
	 UserEntity queryUserByEmail(String email);
     
	 /**
	  * 根据手机号码以及终端类型进行查询
	  * @param cellPhoneNum 手机号码
	  * @return
	  */
	 UserEntity queryUserByCellPhone(String cellPhoneNum);
     

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
      
	 /**
	  * 根据用户名称进行查询
	  * @param userName
	  * @return
	  */
	 UserEntity queryUserByUserName(String userName);
     
}
