package com.kittydaddy.service.system.impl;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.facade.convert.system.UserConvert;
import com.kittydaddy.facade.dto.system.UserDto;
import com.kittydaddy.facade.dto.system.request.UserRequest;
import com.kittydaddy.facade.dto.system.response.UserResponse;
import com.kittydaddy.metadata.system.dao.UserMapper;
import com.kittydaddy.metadata.system.domain.UserEntity;
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
	
	public UserResponse queryUserById(long userId) {
		UserEntity userEntity = userMapper.queryUserById(userId);
		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(userEntity, response);
		return response;
	}

	public UserResponse queryUserByEmail(String email, Integer terminalType) {
		UserEntity userEntity = userMapper.queryUserByEmail(email,terminalType);
		UserResponse response = UserConvert.convertEntity2Response(userEntity);
		return response;
	}

	public UserResponse queryUserByCellPhone(String cellPhoneNum,Integer terminalType) {
		UserEntity userEntity = userMapper.queryUserByCellPhone(cellPhoneNum, terminalType);
		UserResponse response = UserConvert.convertEntity2Response(userEntity);
		return response;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<UserDto> queryUsersByPage(String name,Integer status,Long tenantId,Integer pageIndex, Integer pageSize) {
			PageHelper.startPage(pageIndex, pageSize, true, null, true);
			List<UserEntity> userEntities = userMapper.queryUsersByName(name,status,tenantId);
			PageInfo pageInfo = new PageInfo(userEntities);
			List<UserDto> userDtos = UserConvert.convertEntity2Dto(userEntities);
			pageInfo.setList(userDtos);
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
         if(CollectionUtils.isNotEmpty(ids)){
        	 for(Long id : ids){
        		 UserEntity entity = userMapper.selectByPrimaryKey(id);
        		 if(entity!=null){
        			 entity.setStatus(StatusEnum.DELETE.getValue());
        			 entity.setUpdateTime(new Date());
        			 userMapper.updateByPrimaryKey(entity);
        		 }
        	 }
         }		
	}
    
	@Override
	public void updateApartUserInfo(UserRequest request) {
		UserEntity oldEntity = userMapper.selectByPrimaryKey(Long.parseLong(request.getId()));
		Integer oldStatus = oldEntity.getStatus();
		UserEntity entity = UserConvert.convertApartInfo2Entity(request,oldEntity);
		entity.setUpdateTime(new Date());
		entity.setStatus(oldStatus);
		userMapper.updateByPrimaryKey(entity);
	}
}
