package com.kittydaddy.service.system.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.enums.LoginTypeEnum;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.common.utils.KCryptogramUtil;
import com.kittydaddy.facade.convert.system.UserConvert;
import com.kittydaddy.facade.dto.system.UserDto;
import com.kittydaddy.facade.dto.system.request.UserRequest;
import com.kittydaddy.facade.dto.system.response.UserResponse;
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
	
	public UserResponse queryUserById(long userId) {
		UserEntity userEntity = userMapper.queryUserById(userId);
		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(userEntity, response);
		return response;
	}

	public UserResponse queryUserByEmail(String email) {
		UserEntity userEntity = userMapper.queryUserByEmail(email);
		UserResponse response = UserConvert.convertEntity2Response(userEntity);
		return response;
	}

	public UserResponse queryUserByCellPhone(String cellPhoneNum) {
		UserEntity userEntity = userMapper.queryUserByCellPhone(cellPhoneNum);
		UserResponse response = UserConvert.convertEntity2Response(userEntity);
		return response;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<UserDto> queryUsersByPage(String name,Integer status,Long tenantId,Integer pageIndex, Integer pageSize) {
			PageHelper.startPage(pageIndex, pageSize, true, null, true);
//			List<UserEntity> userEntities = userMapper.queryUsersByName(name,status,tenantId);
			PageInfo pageInfo =null;
//			List<UserDto> userDtos = UserConvert.convertEntity2Dto(userEntities);
//			pageInfo.setList(userDtos);
			return pageInfo;
	}

	public void saveUser(UserRequest request) {
		   logger.info("request{}"+request);
		   UserEntity entity = UserConvert.convertRequest2Entity(request);
		   entity.setStatus(StatusEnum.NORMAL.getValue());
		   userMapper.insert(entity);
	}

	public void updateUser(UserResponse response) {
		 logger.info("response{}"+response);
         UserEntity entity = UserConvert.converResponse2Entity(response);	
         userMapper.updateByPrimaryKey(entity);
	}

	public void delete(Set<Long> ids) {
//         if(CollectionUtils.isNotEmpty(ids)){
//        	 for(Long id : ids){
//        		 UserEntity entity = userMapper.selectByPrimaryKey(id);
//        		 if(entity!=null){
//        			 entity.setStatus(StatusEnum.DELETE.getValue());
//        			 entity.setUpdateTime(new Date());
//        			 userMapper.updateByPrimaryKey(entity);
//        		 }
//        	 }
//         }		
	}
    
	@Override
	public void updateApartUserInfo(UserRequest request) {
//		UserEntity oldEntity = userMapper.selectByPrimaryKey(Long.parseLong(request.getId()));
//		Integer oldStatus = oldEntity.getStatus();
//		UserEntity entity = UserConvert.convertApartInfo2Entity(request,oldEntity);
//		entity.setUpdateTime(new Date());
//		entity.setStatus(oldStatus);
//		userMapper.updateByPrimaryKey(entity);
	}

	@Override
	@Transactional
	public void saveUpdateUser(Map<String, Object> params) {
		 String salt = KCryptogramUtil.getSalt();
  	     String tenantId = params.get("tenantId")==null?"":params.get("tenantId").toString();
         String userName = params.get("userName")==null?"":params.get("userName").toString();
         String mobile = params.get("mobile")==null?"":params.get("mobile").toString();
         Integer sex = params.get("sex")==null?0:Integer.parseInt(params.get("sex").toString());
         String userPwd = params.get("userPwd")==null?"":params.get("userPwd").toString();
         String encodePwd = KCryptogramUtil.getEncryptPassword(salt,userPwd, mobile, null);
         String tenantName = null;
         TenantEntity tenantEntity = tenantMapper.selectByPrimaryKey(tenantId);
         if(tenantEntity!=null) tenantName = tenantEntity.getName();
         
         if(params.get("id") == null){//表示新增
        	 UserEntity userEntity = new UserEntity();
        	 userEntity.setCellPhoneNum(mobile);
        	 userEntity.setCreateTime(new Date());
             userEntity.setTenantId(tenantId);
             userEntity.setTenantName(tenantName);
             userEntity.setSalt(salt);
             userEntity.setUserName(userName);
             userEntity.setSex(sex);
             userEntity.setUserPwd(encodePwd);
             userEntity.setStatus(StatusEnum.NORMAL.getValue());
             userEntity.setLoginType(LoginTypeEnum.SYSTEM_CELLPHONE_LOGIN.getType());
             userMapper.insert(userEntity);
             
         }else{//表示修改
        	 UserEntity oldUserEntity = userMapper.selectByPrimaryKey(params.get("id").toString());
        	 if(oldUserEntity == null) logger.error(String.format("用户Id%s对应的数据为空",params.get("id").toString()));
        	 oldUserEntity.setUpdateTime(new Date());
        	 oldUserEntity.setUserPwd(KCryptogramUtil.getEncryptPassword(oldUserEntity.getSalt(), userPwd, oldUserEntity.getCellPhoneNum(), oldUserEntity.getEmail()));
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
			String encodePassword = KCryptogramUtil.getEncryptPassword(originSalt, oldPassword, userEntity.getCellPhoneNum(), userEntity.getEmail());
			if(userEntity.getUserPwd().equals(encodePassword)) return true;
		}
		return false;
	}
}
