package com.kittydaddy.metadata.tenant.dao;

import com.kittydaddy.metadata.tenant.domain.TenantTeacherClassEntity;

public interface TenantTeacherClassEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TenantTeacherClassEntity record);

    int insertSelective(TenantTeacherClassEntity record);

    TenantTeacherClassEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TenantTeacherClassEntity record);

    int updateByPrimaryKey(TenantTeacherClassEntity record);
}