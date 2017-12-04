package com.kittydaddy.metadata.tenant.dao;

import com.kittydaddy.metadata.tenant.domain.TenantTeacherCourseEntity;

public interface TenantTeacherCourseEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TenantTeacherCourseEntity record);

    int insertSelective(TenantTeacherCourseEntity record);

    TenantTeacherCourseEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TenantTeacherCourseEntity record);

    int updateByPrimaryKey(TenantTeacherCourseEntity record);
}