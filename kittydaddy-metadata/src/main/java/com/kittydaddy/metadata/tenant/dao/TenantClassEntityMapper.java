package com.kittydaddy.metadata.tenant.dao;
import com.kittydaddy.metadata.tenant.domain.TenantClassEntity;
public interface TenantClassEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TenantClassEntity record);

    int insertSelective(TenantClassEntity record);

    TenantClassEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TenantClassEntity record);

    int updateByPrimaryKey(TenantClassEntity record);
}