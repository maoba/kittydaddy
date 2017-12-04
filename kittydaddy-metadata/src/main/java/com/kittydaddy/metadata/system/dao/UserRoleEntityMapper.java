package com.kittydaddy.metadata.system.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.system.domain.UserRoleEntity;

public interface UserRoleEntityMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleEntity record);

    int insertSelective(UserRoleEntity record);

    UserRoleEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleEntity record);

    int updateByPrimaryKey(UserRoleEntity record);

	List<UserRoleEntity> queryRole(@Param(value = "userId") long userId,@Param(value = "tenantId") long tenantId);

	void deleteByRoleId(@Param(value="roleId") Long roleId);

	void deleteByUserId(@Param(value="userId") Long userId);
}