package com.kittydaddy.metadata.tenant.dao;

import com.kittydaddy.metadata.tenant.domain.TenantDetailEntity;

public interface TenantDetailEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TenantDetailEntity record);

    int insertSelective(TenantDetailEntity record);

    TenantDetailEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TenantDetailEntity record);

    int updateByPrimaryKeyWithBLOBs(TenantDetailEntity record);

    int updateByPrimaryKey(TenantDetailEntity record);
}