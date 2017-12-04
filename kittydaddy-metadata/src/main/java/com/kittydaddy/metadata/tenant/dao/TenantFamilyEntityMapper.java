package com.kittydaddy.metadata.tenant.dao;

import com.kittydaddy.metadata.tenant.domain.TenantFamilyEntity;

public interface TenantFamilyEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TenantFamilyEntity record);

    int insertSelective(TenantFamilyEntity record);

    TenantFamilyEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TenantFamilyEntity record);

    int updateByPrimaryKey(TenantFamilyEntity record);
}