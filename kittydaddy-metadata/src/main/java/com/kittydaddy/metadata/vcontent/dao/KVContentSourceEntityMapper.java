package com.kittydaddy.metadata.vcontent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;

public interface KVContentSourceEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(KVContentSourceEntity record);

    int insertSelective(KVContentSourceEntity record);

    KVContentSourceEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KVContentSourceEntity record);

    int updateByPrimaryKey(KVContentSourceEntity record);

	List<KVContentSourceEntity> findByRelativeTypeAndRelativeId(@Param(value="relativeType")String relativeType,@Param(value="relativeId") String relativeId);
}