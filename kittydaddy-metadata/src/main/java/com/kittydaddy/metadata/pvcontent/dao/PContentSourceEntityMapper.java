package com.kittydaddy.metadata.pvcontent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.pvcontent.domain.PContentSourceEntity;

public interface PContentSourceEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(PContentSourceEntity record);

    int insertSelective(PContentSourceEntity record);

    PContentSourceEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PContentSourceEntity record);

    int updateByPrimaryKey(PContentSourceEntity record);

	List<PContentSourceEntity> findSourceByRelativeTypeAndRelativeId(@Param(value="relativeType")String relativeType,@Param(value="relativeId") String relativeId);
}