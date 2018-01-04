package com.kittydaddy.metadata.system.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.system.domain.UserEntity;

public interface UserMapper {
    int deleteByPrimaryKey(String id);
    
    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

	UserEntity findByName(@Param(value="name") String name);

	UserEntity queryUserById(@Param(value="id") long userId);

	UserEntity queryUserByEmail(@Param(value="email") String email);
	
	UserEntity queryUserByCellPhone(@Param(value="cellPhoneNum") String cellPhoneNum);

	List<UserEntity> queryUsersByName(@Param(value="name") String name,@Param(value="status") Integer status,@Param(value="tenantId") String tenantId);
}