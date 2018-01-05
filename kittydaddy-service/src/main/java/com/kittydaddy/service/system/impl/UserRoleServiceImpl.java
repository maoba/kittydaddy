package com.kittydaddy.service.system.impl;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kittydaddy.facade.convert.system.UserRoleConvert;
import com.kittydaddy.facade.dto.system.UserRoleDto;
import com.kittydaddy.facade.dto.system.request.UserRoleRequest;
import com.kittydaddy.metadata.system.dao.RoleEntityMapper;
import com.kittydaddy.metadata.system.dao.UserMapper;
import com.kittydaddy.metadata.system.dao.UserRoleEntityMapper;
import com.kittydaddy.metadata.system.domain.RoleEntity;
import com.kittydaddy.metadata.system.domain.UserEntity;
import com.kittydaddy.metadata.system.domain.UserRoleEntity;
import com.kittydaddy.service.system.UserRoleService;
/**
 * @author kitty daddy
 * 用户角色服务
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
    private UserRoleEntityMapper userRoleMapper;
	
	@Autowired
	private RoleEntityMapper roleMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public void deleteByRoleId(String roleId) {
		userRoleMapper.deleteByRoleId(roleId);
	}

	@Override
	@Transactional
	public void deleteByUserId(String userId) {
		userRoleMapper.deleteByUserId(userId);
	}

}
