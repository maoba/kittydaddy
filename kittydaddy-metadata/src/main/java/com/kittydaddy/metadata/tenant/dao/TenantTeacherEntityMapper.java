package com.kittydaddy.metadata.tenant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.tenant.domain.TenantTeacherEntity;

public interface TenantTeacherEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TenantTeacherEntity record);

    int insertSelective(TenantTeacherEntity record);

    TenantTeacherEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TenantTeacherEntity record);

    int updateByPrimaryKey(TenantTeacherEntity record);

	List<TenantTeacherEntity> queryTeachersByPage(@Param(value="name") String name,
			                               @Param(value="nickName") String nickName,@Param(value="tenantId")Long tenantId);
}