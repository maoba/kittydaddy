package com.kittydaddy.service.system.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kittydaddy.metadata.system.dao.UserRoleEntityMapper;
import com.kittydaddy.service.system.UserRoleService;
/**
 * @author kitty daddy
 * 用户角色服务
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
    private UserRoleEntityMapper userRoleMapper;
	

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
