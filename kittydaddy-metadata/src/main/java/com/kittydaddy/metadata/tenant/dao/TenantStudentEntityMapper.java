package com.kittydaddy.metadata.tenant.dao;

import com.kittydaddy.metadata.tenant.domain.TenantStudentEntity;

public interface TenantStudentEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TenantStudentEntity record);

    int insertSelective(TenantStudentEntity record);

    TenantStudentEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TenantStudentEntity record);

    int updateByPrimaryKey(TenantStudentEntity record);
}