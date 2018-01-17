package com.kittydaddy.service.system.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.enums.LoginTypeEnum;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.common.utils.KCryptogramUtil;
import com.kittydaddy.common.utils.KDateUtils;
import com.kittydaddy.common.utils.KStringUtils;
import com.kittydaddy.metadata.system.dao.RoleEntityMapper;
import com.kittydaddy.metadata.system.dao.UserMapper;
import com.kittydaddy.metadata.system.dao.UserRoleEntityMapper;
import com.kittydaddy.metadata.system.domain.RoleEntity;
import com.kittydaddy.metadata.system.domain.UserEntity;
import com.kittydaddy.metadata.system.domain.UserRoleEntity;
import com.kittydaddy.metadata.tenant.dao.TenantEntityMapper;
import com.kittydaddy.metadata.tenant.domain.TenantEntity;
import com.kittydaddy.service.system.UserService;
/**
 * @author kitty daddy
 * 用户服务接口实现
 */
@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleEntityMapper userRoleMapper;
	
	@Autowired
	private RoleEntityMapper roleMapper;
	
	@Autowired
	private TenantEntityMapper tenantMapper;
	
	@Override
	public PageInfo<UserEntity> queryUserByPage(String name, String tenantId,Integer status, Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex,pageSize, true, null, true);
		List<UserEntity> list = userMapper.queryUsersByName(name, status, tenantId);
		PageInfo<UserEntity> page = new PageInfo<UserEntity>(list);
		return page;
	}
	
	public UserEntity queryUserById(String userId) {
		UserEntity userEntity = userMapper.selectByPrimaryKey(userId);
		return userEntity;
	}

	public UserEntity queryUserByEmail(String email) {
		UserEntity userEntity = userMapper.queryUserByEmail(email);
		return userEntity;
	}

	public UserEntity queryUserByCellPhone(String cellPhoneNum) {
		UserEntity userEntity = userMapper.queryUserByCellPhone(cellPhoneNum);
		return userEntity;
	}
	


	@Override
	@Transactional
	public void saveUpdateUser(Map<String, Object> params) {
		 String salt = KCryptogramUtil.getSalt();
  	     String tenantId = params.get("tenantId")==null?"":params.get("tenantId").toString();
  	     String tenantName = params.get("tenantName")==null?"":params.get("tenantName").toString();
         String userName = params.get("userName")==null?"":params.get("userName").toString();
         String mobile = params.get("mobile")==null?"":params.get("mobile").toString();
         Integer sex = params.get("sex")==null?0:Integer.parseInt(params.get("sex").toString());
         String userPwd = params.get("userPwd")==null?"":params.get("userPwd").toString();
         userPwd = "123456";
         String encodePwd = KCryptogramUtil.getEncryptPassword(salt,userPwd, userName);
         String realName = params.get("realName")==null?"":params.get("realName").toString();
         String birthday = params.get("birthday")==null?"":params.get("birthday").toString();
         String province = params.get("province")==null?"":params.get("province").toString();
         String city = params.get("city")==null?"":params.get("city").toString();
         String area = params.get("area")==null?"":params.get("area").toString();
         String email = params.get("email") == null?"":params.get("email").toString();
         String hobby = params.get("hobby") == null?"":params.get("hobby").toString();
         
         if(params.get("id") == null){//表示新增
        	 TenantEntity tenantEntity = tenantMapper.selectByPrimaryKey(tenantId);
             if(tenantEntity!=null) tenantName = tenantEntity.getName();
        	 UserEntity userEntity = new UserEntity();
        	 userEntity.setCellPhoneNum(mobile);
        	 userEntity.setCreateTime(new Date());
             userEntity.setTenantId(tenantId);
             userEntity.setTenantName(tenantName);
             userEntity.setSalt(salt);
             userEntity.setUserName(userName);
             userEntity.setSex(sex);
             userEntity.setUserPwd(encodePwd);
             userEntity.setStatus(StatusEnum.VALID.getValue());
             userEntity.setLoginType(LoginTypeEnum.SYSTEM_CELLPHONE_LOGIN.getType());
             userMapper.insert(userEntity);
             
         }else{//表示修改
        	 UserEntity oldUserEntity = userMapper.selectByPrimaryKey(params.get("id").toString());
        	 if(oldUserEntity == null) logger.error(String.format("用户Id%s对应的数据为空",params.get("id").toString()));
        	 oldUserEntity.setUpdateTime(new Date());
        	 if(KStringUtils.isNotEmpty(realName)) oldUserEntity.setRealName(realName);
        	 if(KStringUtils.isNotEmpty(tenantName)) oldUserEntity.setTenantName(tenantName);
        	 if(params.get("sex") !=null) oldUserEntity.setSex(sex);
        	 if(KStringUtils.isNotEmpty(mobile)) oldUserEntity.setCellPhoneNum(mobile);
        	 if(KStringUtils.isNotEmpty(birthday)) oldUserEntity.setBirthday(KDateUtils.parseDate(birthday, "yyyy-MM-dd"));
        	 if(KStringUtils.isNotEmpty(province)) oldUserEntity.setProvince(province);
        	 if(KStringUtils.isNotEmpty(city)) oldUserEntity.setCity(city);
        	 if(KStringUtils.isNotEmpty(area)) oldUserEntity.setArea(area);
        	 if(KStringUtils.isNotEmpty(email)) oldUserEntity.setEmail(email);
        	 if(KStringUtils.isNotEmpty(hobby)) oldUserEntity.setHobby(hobby);
        	 
        	 TenantEntity tenantEntity = tenantMapper.selectByPrimaryKey(tenantId);
        	 if(tenantEntity!=null && params.get("tenantName")!=null) {
        		 tenantEntity.setName(tenantName);
        		 tenantMapper.updateByPrimaryKey(tenantEntity);
        	 }
        	 if(KStringUtils.isNotEmpty(userPwd)) oldUserEntity.setUserPwd(KCryptogramUtil.getEncryptPassword(oldUserEntity.getSalt(), userPwd, userName));
        	 userMapper.updateByPrimaryKey(oldUserEntity);
         }	
	}

	@Override
	public boolean saveUserRole(Map<String, Object> params) {
        try{ String userId = params.get("userId").toString();
	         UserEntity userEntity = userMapper.selectByPrimaryKey(userId);
	         if(userEntity == null) logger.error(String.format("用户Id%s对应的数据为空",userId));
	         //解绑之前的用户角色关系
	         userRoleMapper.deleteByUserId(userId);
	         boolean removeSuccess = params.remove("userId", userId);
	         if(removeSuccess){//获取剩下的所有的角色并且进行绑定
	        	 if(params.keySet().size()==0) return false;
	        	 for(String key : params.keySet()){
	        		 String roleId = params.get(key).toString();
	        		 RoleEntity roleEntity = roleMapper.selectByPrimaryKey(roleId);
	        		 if(null == roleEntity) logger.error(String.format("角色Id%s对应的数据为空", roleId));
	        		 UserRoleEntity userRole = new UserRoleEntity();
	        		 userRole.setRoleCode(roleEntity.getRoleCode());
	        		 userRole.setRoleId(roleId);
	        		 userRole.setRoleName(roleEntity.getRoleName());
	        		 userRole.setTenantId(userEntity.getTenantId());
	        		 userRole.setUserId(userId);
	        		 userRole.setUserName(userEntity.getUserName());
	        		 userRoleMapper.insert(userRole);
	        	 }
	         }
         }catch(Exception e){
        	 logger.error(String.format("保存用户角色关系出错，错误信息是：%s", e));
        	 return false;
         }
         return true;
	}

	@Override
	public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);		
	}

	@Override
	public boolean checkPassword(String oldPassword, String userId) {
		UserEntity userEntity = userMapper.selectByPrimaryKey(userId);
		if(null!= userEntity){
			String originSalt = userEntity.getSalt();
			String encodePassword = KCryptogramUtil.getEncryptPassword(originSalt, oldPassword, userEntity.getUserName());
			if(userEntity.getUserPwd().equals(encodePassword)) return true;
		}
		return false;
	}

	@Override
	public UserEntity queryUserByUserName(String userName) {
		UserEntity userEntity = userMapper.queryUserByUserName(userName);
		return userEntity;
	}
}
